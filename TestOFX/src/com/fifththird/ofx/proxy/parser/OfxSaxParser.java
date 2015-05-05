/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy.parser;

import java.io.IOException;
import java.io.Reader;
import org.apache.log4j.Logger;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import android.util.Log;

/**
 * Very simplistic SGML SAX-style parser for parsing OFX messages. Expects simple tags with no
 * attributes (eg: &lt;FOO&gt;), and no &lt; characters other than in tags. Differs from an XML sax
 * parser in that each tag (opening and closing) is considered a separate element. That is, both the
 * opening tag and closing tag (if present, not required in SGML/OFX) will generate startElement()
 * and endElement() calls.
 */
public class OfxSaxParser
{

	private static final Logger log = Logger.getLogger(OfxSaxParser.class);

	private ContentHandler contentHandler;
	private Reader input;

	public OfxSaxParser(Reader input, ContentHandler contentHandler)
	{
		this.input = input;

		this.contentHandler = contentHandler;
		if (contentHandler == null)
		{
			throw new IllegalArgumentException("Content Handler must be provided");
		}
	}

	public void parse() throws SAXException
	{
		if (input == null)
		{
			return;
		}

		try
		{

			contentHandler.startDocument();
			// Read up to the first tag (should be empty, but just in case...)
			String preamble = readToTag();
			if (preamble != null && preamble.length() > 0)
			{
				contentHandler.characters(preamble.toCharArray(), 0, preamble.length());
			}

			// Read tags
			String tagName = null;
			while ((tagName = readTagName()) != null)
			{
				// Fire event for element start
				contentHandler.startElement(null, tagName, null, null);

				// Read tag contents
				String tagContent = readToTag();

				// Fire event for character data (if present)
				if (tagContent != null)
				{
					contentHandler.characters(tagContent.toCharArray(), 0, tagContent.length());
				}

				// Fire event for element end
				contentHandler.endElement(null, tagName, null);
				Log.d("OfxSaxParser", "parse:: *****TagContent*****" + tagContent);
				Log.d("OfxSaxParser", "parse:: *****TagName*****" + tagName);
			}

			contentHandler.endDocument();
		}
		catch (IOException ioe)
		{
			throw new SAXException("IO error parsing input", ioe);
		}
	}

	/**
	 * Reads until a specified character.
	 */
	private String readToChar(char target) throws IOException
	{
		StringBuffer buf = new StringBuffer(200);
		while (true)
		{
			int iChar = input.read();
			// log.debug("Read char: " + iChar);
			if (iChar == -1)
			{
				break;
			}
			char c = (char) iChar;
			if (target == c)
			{
				break;
			}
			else
			{
				buf.append(c);
			}
		}
		// Hit either target or end of input. Return accumulated
		// characters (or null if nothing read)
		return buf.length() == 0 ? null : buf.toString();
	}

	/**
	 * Reads until the start of the next
	 */
	private String readToTag() throws IOException
	{
		String content = readToChar('<');
		return content;
	}

	/**
	 * Reads a tag name (any characters up to &gt;
	 */
	private String readTagName() throws IOException, SAXException
	{
		String tag = readToChar('>');
		return tag;
	}
}
