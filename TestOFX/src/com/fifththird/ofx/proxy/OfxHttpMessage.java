/* Copyright 2011 Fifth Third Bank. All rights reserved. */
/**
 * 
 */
package com.fifththird.ofx.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fifththird.ofx.proxy.parser.OfxDomParser;

/**
 * Extension of @link {@link HttpMessage} containing utility methods for handling OFX messages.
 */
public class OfxHttpMessage extends HttpMessage
{

	private static final Logger log = Logger.getLogger(OfxHttpMessage.class);

	private XPath xpath = XPathFactory.newInstance().newXPath();

	/**
	 * Pattern for an OFX header
	 */
	private static final Pattern OFX_HEADER_PATTERN = Pattern.compile("^([^:]*):(.*)$");

	/**
	 * Elements that require a closing tag when expressed in an OFX message regardless of whether or
	 * not they are aggregates (contain child elements).
	 */
	private static final String[] ELEMENTS_REQUIRING_CLOSING_TAG = new String[] { "EXTDPMTDSC", "MSGBODY" };

	/**
	 * XPath expression for extracting signon message set
	 */
	private static final String XPATH_SIGNON_MESSAGE_SET = "//SIGNONMSGSRQV1";

	/**
	 * XPath expression for extracting billpay request message set
	 */
	private static final String XPATH_BILLPAY_REQUEST_MESSAGE_SET = "//BILLPAYMSGSRQV1";

	/**
	 * XPath expression for extracting billpay response message set
	 */
	private static final String XPATH_BILLPAY_RESPONSE_MESSAGE_SET = "//BILLPAYMSGSRSV1";

	private List<OfxHeader> ofxHeaders;
	private Document ofxMessage;

	public OfxHttpMessage(List<Header> httpHeaders, String httpBody, int responseCode)
	{
		super(httpHeaders, httpBody, responseCode);
		// Parse into OFX headers and OFX message document
		ofxHeaders = parseOfxHeaders(httpBody);
		ofxMessage = parseOfxMessage(httpBody);
	}

	/**
	 * Constructor that creates an OFX message from supplied HTTP {@link Header} s ,
	 * {@link OfxHeader}s, and message set {@link Node}s.
	 */
	public OfxHttpMessage(List<Header> httpHeaders, List<OfxHeader> ofxHeaders, Node... messageSets)
	{
		super(httpHeaders, null);
		this.ofxHeaders = ofxHeaders;

		// Construct the ofxMessage
		ofxMessage = OfxDomParser.parse("<OFX></OFX>");
		for (Node messageset : messageSets)
		{
			appendMessageSet(messageset);
		}
	}

	public OfxHttpMessage(List<Header> httpHeaders, String httpBody)
	{
		this(httpHeaders, httpBody, HttpServletResponse.SC_OK);
	}

	public OfxHttpMessage(PostMethod post) throws IOException
	{
		this(Arrays.asList(post.getResponseHeaders()), post.getResponseBodyAsString(), post.getStatusCode());
	}

	/**
	 * Parses the OFX headers from an HTTP message body.
	 * 
	 * @see OfxHeader
	 */
	private List<OfxHeader> parseOfxHeaders(String httpBody)
	{
		BufferedReader reader = new BufferedReader(new StringReader(httpBody));
		List<OfxHeader> headers = new LinkedList<OfxHeader>();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				if (StringUtils.isBlank(line))
				{
					break;
				}
				Matcher m = OFX_HEADER_PATTERN.matcher(line);
				if (m.matches())
				{
					String name = m.group(1);
					String value = m.group(2);
					OfxHeader header = new OfxHeader(name, value);
					headers.add(header);
				}
				else
				{
					throw new IllegalArgumentException("Invalid OFX header line: " + line);
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error reading OFX headers", e);
		}
		return headers;
	}

	/**
	 * Parses the OFX message (SGML document) from an HTTP message body into a {@link Document},
	 * using {@link OfxDomParser}. OFX messages are an SGML dialect, not XML, so the resulting
	 * Document must be converted back to SGML before using it for OFX messaging. Parsing into a
	 * Document makes it convenient to manipulate the message contents with readily-available
	 * XML-based tools.
	 */
	private Document parseOfxMessage(String httpBody)
	{
		BufferedReader reader = new BufferedReader(new StringReader(httpBody));
		// Skip past the OFX headers
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				if (StringUtils.isBlank(line))
				{
					break;
				}
			}
			if (line == null)
			{
				throw new RuntimeException("Unexpected end of message while reading OFX headers");
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error reading OFX headers", e);
		}

		// Parse the remainder as OFX message
		return OfxDomParser.parse(reader);
	}

	@Override
	public String getBody()
	{
		// Generate OFX-formatted message
		StringBuffer sb = new StringBuffer(5000);

		// Output OFX headers
		for (OfxHeader ofxHeader : ofxHeaders)
		{
			sb.append(ofxHeader.toString()).append("\r\n");
		}
		sb.append("\r\n");

		// Output OFX message body
		String body = getOfxBodyAsString();
		sb.append(body);

		return sb.toString();
	}

	/**
	 * Converts OFX message body to String
	 */
	public String getOfxBodyAsString()
	{
		StringBuffer out = new StringBuffer(5000);
		appendNode(ofxMessage.getDocumentElement(), out);
		return out.toString();
	}

	/**
	 * Appends a {@link Node} from an OFX message {@link Document} to a {@link StringBuffer},
	 * formatted as OFX SGML
	 */
	private static void appendNode(Node n, StringBuffer out)
	{
		if (n == null)
		{
			return;
		}
		switch (n.getNodeType())
		{
			case Node.ELEMENT_NODE:
				String name = n.getNodeName();
				out.append('<').append(name).append('>');
				if (n.hasChildNodes())
				{
					NodeList kids = n.getChildNodes();
					for (int i = 0; i < kids.getLength(); i++)
					{
						Node child = kids.item(i);
						appendNode(child, out);
					}
				}
				// Add closing tag if there are any element children
				if (hasElementChildren(n) || elementRequiresClosingTag(name))
				{
					out.append("</").append(name).append('>');
				}
				break;
			case Node.TEXT_NODE:
				out.append(n.getNodeValue());
				break;
			default:
				log.info("Unknown/unexpected node type " + n.getNodeType() + " for node " + n);
				break;
		}
	}

	/**
	 * Return true if the element identified by name requires a closing tag, regardless of whether
	 * or not it has children elements.
	 */
	protected static boolean elementRequiresClosingTag(String elementName)
	{
		if (ArrayUtils.contains(ELEMENTS_REQUIRING_CLOSING_TAG, elementName))
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the {@link Node} has any children that are {@link Element}s
	 */
	private static boolean hasElementChildren(Node n)
	{
		if (n.hasChildNodes())
		{
			NodeList kids = n.getChildNodes();
			for (int i = 0; i < kids.getLength(); i++)
			{
				if (Node.ELEMENT_NODE == kids.item(i).getNodeType())
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void setBody(String body)
	{
		throw new UnsupportedOperationException("Do not directly manipulate the body of OfxHttpMessage." + "Use the methods on OfxHttpMessage instead.");
	}

	public List<OfxHeader> getOfxHeaders()
	{
		return ofxHeaders;
	}

	/**
	 * Replaces value of an existing OFX header. Will not add header if it does not already exist.
	 */
	public void replaceOfxHeader(String name, String newValue)
	{
		for (OfxHeader header : ofxHeaders)
		{
			if (StringUtils.equals(header.getName(), name))
			{
				if (log.isDebugEnabled())
				{
					log.debug("Replacing OFX header '" + name + "'. Old value=" + header.getValue() + ", new value=" + newValue);
				}
				header.setValue(newValue);
			}
		}
	}

	/**
	 * Retrieves a node matching an XPath expression.
	 * 
	 * @return Node matching expression or null if no match found
	 */
	public Node getNode(String xpathExpr)
	{
		Node n = null;
		try
		{
			n = (Node) xpath.evaluate(xpathExpr, ofxMessage, XPathConstants.NODE);
		}
		catch (XPathExpressionException e)
		{
			throw new IllegalArgumentException("Invalid XPath expression", e);
		}
		return n;
	}

	/**
	 * Makes copy of the signon message set, if present.
	 * 
	 * @return Clone of the Signon message set, or null if not present
	 */
	public Node cloneSignonMessageSet()
	{

		Node signonMessageSet = getNode(XPATH_SIGNON_MESSAGE_SET);
		Node clone = null;
		if (signonMessageSet != null)
		{
			clone = signonMessageSet.cloneNode(true);
		}
		else
		{
			log.warn("OFX message does not contain a SONRQ element");
		}
		return clone;
	}

	/**
	 * Removes the billpay message set from the OFX message. <b>NOTE:</b> This method alters the
	 * content of the contained message.
	 * 
	 * @return The extracted message set (or null if not present in message)
	 */
	public Node removeBillpayMessageSet()
	{
		return removeNode(XPATH_BILLPAY_REQUEST_MESSAGE_SET);
	}

	/**
	 * Removes the specified node from the OFX message. If the message contains more than one
	 * instance of this node, only the first instance will be removed. <b>NOTE:</b> This method
	 * alters the content of the contained message.
	 * 
	 * @return The removed nod (or null if not present in message)
	 */
	public Node removeNode(String xpath)
	{
		Node node = getNode(xpath);
		if (node != null)
		{
			// Remove the node from the message
			Node removedNode = node.getParentNode().removeChild(node);
			return removedNode;
		}
		// Not found
		return null;
	}

	/**
	 * Removes all instances of the specified node from the OFX message.<b>NOTE:</b> This method
	 * alters the content of the contained message.
	 */
	public void removeAllInstancesOfNode(String xpath)
	{
		Node removedNode = null;
		do
		{
			removedNode = removeNode(xpath);
		} while (removedNode != null);
	}

	/**
	 * Makes a copy of the billpay response message set from the OFX message. Does not alter the
	 * message body.
	 * 
	 * @return Clone of the billpay response message set, or null if not present
	 */
	public Node extractBillpayResponseMessageSet()
	{
		Node messageSet = getNode(XPATH_BILLPAY_RESPONSE_MESSAGE_SET);
		Node clone = null;
		if (messageSet != null)
		{
			clone = messageSet.cloneNode(true);
		}
		else
		{
			log.warn("OFX message does not contain a BILLPAYMSGSRV1 element");
		}
		return clone;
	}

	/**
	 * Appends a simple tag/value element to the specified parent node
	 */
	public void appendTag(Node parent, String tagName, String tagValue)
	{
		Element newChild = ofxMessage.createElement(tagName);
		newChild.appendChild(ofxMessage.createTextNode(tagValue));
		appendChild(parent, newChild);
	}

	/**
	 * Appends a child Node to the specified node.
	 */
	public void appendChild(Node parent, Node newChild)
	{
		Node imported = ofxMessage.importNode(newChild, true);
		parent.appendChild(imported);
	}

	/**
	 * Appends a message set to the OFX message. Message set will be inserted as last tag before the
	 * closing &lt;/OFX&gt; tag. <b>NOTE:</b> This method alters the content of the contained
	 * message.
	 */
	public void appendMessageSet(Node messageset)
	{
		// Note that this is not compliant with the OFX specification, which
		// states that message sets must be in a specific order (OFX 1.0.3 spec
		// section 2.4.5.2). I've seen several examples (from the FFusion OFX
		// server) where this is violated, so I'm assuming it's OK.
		Element docElem = ofxMessage.getDocumentElement();
		appendChild(docElem, messageset);
	}

	/**
	 * Replaces the values ({@link Node#setNodeValue(String)} of all nodes matching the supplied
	 * XPath expression. Usually this will be used to replace the text contents of elements, in
	 * which case the expression should end with "/text()".
	 */
	public void replaceAll(String xpathExpr, String newValue)
	{
		try
		{
			XPathExpression xpe = xpath.compile(xpathExpr);

			// Change node value(s)
			NodeList matches = (NodeList) xpe.evaluate(ofxMessage, XPathConstants.NODESET);
			for (int i = 0; i < matches.getLength(); i++)
			{
				matches.item(i).setNodeValue(newValue);
			}
		}
		catch (XPathExpressionException e)
		{
			throw new IllegalArgumentException("Invalid XPath expressiong", e);
		}
	}

	/**
	 * Returns the text contents of the first element matching the supplied XPath expression. There
	 * is an implied "/text()" at the end of the expression, it will be appended automatically if
	 * not present.
	 */
	public String getElementValue(String xpathExpr)
	{
		String xpr = xpathExpr;
		if (!"text()".equalsIgnoreCase(StringUtils.right(xpathExpr, 6)))
		{
			xpr += "/text()";
		}
		try
		{
			XPathExpression xpe = xpath.compile(xpr);

			// Extract node value(s)
			String val = (String) xpe.evaluate(ofxMessage, XPathConstants.STRING);
			return StringUtils.trimToNull(val);
		}
		catch (XPathExpressionException e)
		{
			throw new IllegalArgumentException("Invalid XPath expressiong", e);
		}
	}
}
