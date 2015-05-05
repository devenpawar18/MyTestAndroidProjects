/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy.parser;

import java.io.StringReader;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OfxSaxParserTest extends TestCase
{

	private static final Logger log = Logger.getLogger(OfxSaxParserTest.class);

	String msg = "<OFX><SIGNONMSGSRSV1><SONRS><STATUS><CODE>0<SEVERITY>INFO" + "<MESSAGE>Request was successful.</STATUS>" + "<DTSERVER>20110201091855[-5:EST]<LANGUAGE>ENG<FI><ORG>Fifth Third Bank" + "<FID>5829</FI></SONRS></SIGNONMSGSRSV1>" + "<BILLPAYMSGSRSV1><PMTSYNCRS><TOKEN>-1<BANKACCTFROM>" + "<BANKID>067091719<ACCTID>7432534688<ACCTTYPE>CHECKING" + "</BANKACCTFROM><PMTTRNRS><TRNUID>9615CEE6-7ADE-1000-8C8D-DFB7B54D0026" + "<STATUS><CODE>2000<SEVERITY>ERROR" + "<MESSAGE>General system error. [Wrapping synchronization request failed.]" + "</STATUS></PMTTRNRS></PMTSYNCRS></BILLPAYMSGSRSV1></OFX>";

	public void testParser() throws Exception
	{
		ContentHandler ch = new TestContentHandler();
		OfxSaxParser parser = new OfxSaxParser(new StringReader(msg), ch);
		parser.parse();
	}

	class TestContentHandler extends DefaultHandler
	{

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			String data = new String(ch, start, length);
			log.debug("Character data: " + data);
		}

		@Override
		public void startDocument() throws SAXException
		{
			log.debug("Start Document");
		}

		@Override
		public void endDocument() throws SAXException
		{
			log.debug("End Document");
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			log.debug("Start Element: " + localName);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			log.debug("End Element: " + localName);
		}
	}
}
