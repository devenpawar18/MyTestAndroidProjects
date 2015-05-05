/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy.parser;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OfxDomParserTest extends TestCase
{
	private Logger log = Logger.getLogger(this.getClass());

	public void testParse() throws Exception
	{
		// <ROOT>
		// ..<A>
		// ..<B>BLAH
		// ..<C>
		// ....<D>INNER1
		// ....<E>INNER2
		// ..</C>
		// ..<F>
		// </ROOT>
		String input = "<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		Document doc = OfxDomParser.parse(input);
		assertNotNull(doc);
		log.info("Parsed document:" + documentToString(doc));
		NodeList nl = doc.getChildNodes();
		assertNotNull(nl);
		assertEquals(1, nl.getLength());
		// Root node
		Node n = nl.item(0);
		assertEquals(Node.ELEMENT_NODE, n.getNodeType());
		assertEquals("ROOT", n.getNodeName());

		// Root children
		nl = n.getChildNodes();
		assertNotNull(nl);
		assertEquals(4, nl.getLength());
		assertEquals("A", nl.item(0).getNodeName());
		assertEquals("B", nl.item(1).getNodeName());
		assertEquals("BLAH", nl.item(1).getChildNodes().item(0).getNodeValue());
		assertEquals("C", nl.item(2).getNodeName());
		assertEquals("F", nl.item(3).getNodeName());

		// Inner elements
		nl = nl.item(2).getChildNodes();
		assertEquals(2, nl.getLength());
		assertEquals("D", nl.item(0).getNodeName());
		assertEquals(1, nl.item(0).getChildNodes().getLength());
		assertEquals("INNER1", nl.item(0).getChildNodes().item(0).getNodeValue());
		assertEquals("E", nl.item(1).getNodeName());
		assertEquals(1, nl.item(1).getChildNodes().getLength());
		assertEquals("INNER2", nl.item(1).getChildNodes().item(0).getNodeValue());
	}

	public void testDeepTree() throws Exception
	{
		String input = "<A><B><C><D><E><F><G>BOTTOM</F></E></D></C></B></A>";
		Document doc = OfxDomParser.parse(input);
		assertNotNull(doc);
		log.info("Parsed document:" + documentToString(doc));
		NodeList nl = doc.getChildNodes();
		assertNotNull(nl);
		assertEquals(1, nl.getLength());

		Node n = doc;
		for (int i = 0; i < 8; i++)
		{
			nl = n.getChildNodes();
			assertEquals(1, nl.getLength());
			n = n.getFirstChild();
		}
		assertEquals("BOTTOM", n.getNodeValue());
	}

	private String documentToString(Document doc) throws Exception
	{
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}

}
