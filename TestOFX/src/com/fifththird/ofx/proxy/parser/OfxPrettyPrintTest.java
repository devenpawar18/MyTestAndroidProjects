/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy.parser;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class OfxPrettyPrintTest extends TestCase {

	private static final Logger log = Logger
			.getLogger(OfxPrettyPrintTest.class);

	String unformatted = "THIS_IS_PREAMBLE:TEXT\nSO_IS_THIS: foo\r\nANDTHIS:BLARG!\n\n<OFX><SIGNONMSGSRSV1><SONRS><STATUS><CODE>0<SEVERITY>INFO"
			+ "<MESSAGE>Request was successful.</STATUS>"
			+ "<DTSERVER>20110201091855[-5:EST]<LANGUAGE>ENG<FI><ORG>Fifth Third Bank"
			+ "<FID>5829</FI><USERPASS>ThisWillBeMasked</SONRS></SIGNONMSGSRSV1>"
			+ "<BILLPAYMSGSRSV1><PMTSYNCRS><TOKEN>-1<BANKACCTFROM>"
			+ "<BANKID>067091719<ACCTID>7432534688<ACCTTYPE>CHECKING"
			+ "</BANKACCTFROM><PMTTRNRS><TRNUID>9615CEE6-7ADE-1000-8C8D-DFB7B54D0026"
			+ "<STATUS><CODE>2000<SEVERITY>ERROR"
			+ "<MESSAGE>General system error. [Wrapping synchronization request failed.]"
			+ "</STATUS></PMTTRNRS></PMTSYNCRS></BILLPAYMSGSRSV1></OFX>"
			+ "THIS_IS_POSTSCRIPT:blah\nAND_SO_IS_THIS";

	String formatted = "THIS_IS_PREAMBLE:TEXT\nSO_IS_THIS: foo\r\nANDTHIS:BLARG!\n\n"
			+ "<OFX>\n"
			+ "   <SIGNONMSGSRSV1>\n"
			+ "      <SONRS>\n"
			+ "         <STATUS>\n"
			+ "            <CODE>0\n"
			+ "            <SEVERITY>INFO\n"
			+ "            <MESSAGE>Request was successful.\n"
			+ "         </STATUS>\n"
			+ "         <DTSERVER>20110201091855[-5:EST]\n"
			+ "         <LANGUAGE>ENG\n"
			+ "         <FI>\n"
			+ "            <ORG>Fifth Third Bank\n"
			+ "            <FID>5829\n"
			+ "         </FI>\n"
			+ "         <USERPASS>[Not Displayed]\n"
			+ "      </SONRS>\n"
			+ "   </SIGNONMSGSRSV1>\n"
			+ "   <BILLPAYMSGSRSV1>\n"
			+ "      <PMTSYNCRS>\n"
			+ "         <TOKEN>-1\n"
			+ "         <BANKACCTFROM>\n"
			+ "            <BANKID>067091719\n"
			+ "            <ACCTID>7432534688\n"
			+ "            <ACCTTYPE>CHECKING\n"
			+ "         </BANKACCTFROM>\n"
			+ "         <PMTTRNRS>\n"
			+ "            <TRNUID>9615CEE6-7ADE-1000-8C8D-DFB7B54D0026\n"
			+ "            <STATUS>\n"
			+ "               <CODE>2000\n"
			+ "               <SEVERITY>ERROR\n"
			+ "               <MESSAGE>General system error. [Wrapping synchronization request failed.]\n"
			+ "            </STATUS>\n"
			+ "         </PMTTRNRS>\n"
			+ "      </PMTSYNCRS>\n"
			+ "   </BILLPAYMSGSRSV1>\n"
			+ "</OFX>THIS_IS_POSTSCRIPT:blah\nAND_SO_IS_THIS\n";

	public void testPrint() throws Exception {
		String output = OfxPrettyPrint.prettyPrint(unformatted);
		log.info("Formatted: \n" + output);
		assertEquals(formatted, output);
	}

}
