/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

public class OfxHttpMessageTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetBody() {
		String input = "OFXHEADER:100\nHEADER2:998\n\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		String expectedOutput = "OFXHEADER:100\r\nHEADER2:998\r\n\r\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);

		String output = fixture.getBody();
		assertEquals(expectedOutput, output);
	}

	@Test
	public void testGetBodyAsString() {
		String input = "OFXHEADER:100\n\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		String expectedOutput = "<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);

		String output = fixture.getOfxBodyAsString();
		assertEquals(expectedOutput, output);
	}

	@Test
	public void testGetBodyAsStringWithRequiredClosingTags() {
		String input = "OFXHEADER:100\n\n<ROOT><A><MSGBODY>Msg text<EXTDPMTDSC>payment desc<B></ROOT>";
		String expectedOutput = "<ROOT><A><MSGBODY>Msg text</MSGBODY><EXTDPMTDSC>payment desc</EXTDPMTDSC><B></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);

		String output = fixture.getOfxBodyAsString();
		assertEquals(expectedOutput, output);
	}

	@Test
	public void testGetOfxHeaders() {
		String input = "OFXHEADER:100\nBLARG:YES\n\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);
		List<OfxHeader> ofxHeaders = fixture.getOfxHeaders();
		assertNotNull(ofxHeaders);
		assertEquals(2, ofxHeaders.size());
	}

	@Test
	public void testReplaceOfxHeader() {
		String input = "OFXHEADER:100\nBLARG:YES\n\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);
		List<OfxHeader> ofxHeaders = fixture.getOfxHeaders();
		assertNotNull(ofxHeaders);
		assertTrue(ofxHeaders.contains(new OfxHeader("BLARG", "YES")));
		assertFalse(ofxHeaders.contains(new OfxHeader("BLARG", "SNARL")));
		fixture.replaceOfxHeader("BLARG", "SNARL");
		assertTrue(ofxHeaders.contains(new OfxHeader("BLARG", "SNARL")));

	}

	@Test
	public void testGetElementValue() {
		String ofxMsg = "OFXHEADER:100\n\n<OFX><SIGNONMSGSRQV1><SONRQ><USERID>TheUserId"
				+ "<USERPASS>notUsed<LANGUAGE>FOO"
				+ "<GENUSERKEY>N<LANGUAGE>BAR<FI><ORG>Fifth Third Bank<FID>5829</FI>"
				+ "<APPID>QWIN<APPVER>2000</SONRQ></SIGNONMSGSRQV1></OFX>";

		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				ofxMsg);

		assertEquals("TheUserId", fixture.getElementValue("//SONRQ/USERID"));
		assertEquals("5829", fixture.getElementValue("//FID/text()"));
		assertEquals("FOO", fixture.getElementValue("//LANGUAGE"));

		try {
			fixture.getElementValue("//b/preceding-sibling::flark(text())");
			fail("Did not throw expected exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Invalid XPath"));
		}

	}

	@Test
	public void testInvalidOfxHeader() {
		String input = "Notaheader\n<OFX></OFX>";
		try {
			new OfxHttpMessage(new LinkedList<Header>(), input);
			fail("Did not throw expected exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().indexOf("Invalid") > -1);
		}

	}

	@Test
	public void testParseNoHeaders() {
		try {
			new OfxHttpMessage(new LinkedList<Header>(), "");
			fail("Did not throw expected exception");
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("Unexpected end of message"));
		}
	}

	@Test
	public void testSetBody() {
		try {
			new OfxHttpMessage(new LinkedList<Header>(), "HEADER:100\n\n")
					.setBody("foo");
			fail("Did not throw expected exception");
		} catch (UnsupportedOperationException e) {
			assertTrue(e.getMessage().contains("manipulate the body"));
		}
	}

	@Test
	public void testGetNode() {
		String input = "OFXHEADER:100\n\n<ROOT><A><B>BLAH<C><D>INNER1<E>INNER2</C><F></ROOT>";
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				input);
		Node n = fixture.getNode("/ROOT/C/D/text()");
		assertNotNull(n);
		assertEquals("INNER1", n.getNodeValue());

		try {
			fixture.getNode("//b/preceding-sibling::flark(text())");
			fail("Did not throw expected exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Invalid XPath"));
		}
	}

	@Test
	public void testCloneSignonMessageSet() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><SIGNONMSGSRQV1><SONRQ><USERID>TheUserId"
						+ "<USERPASS>notUsed<LANGUAGE>FOO"
						+ "<GENUSERKEY>N<LANGUAGE>BAR<FI><ORG>Fifth Third Bank<FID>5829</FI>"
						+ "<APPID>QWIN<APPVER>2000</SONRQ></SIGNONMSGSRQV1></OFX>");
		Node n = fixture.cloneSignonMessageSet();
		assertNotNull(n);

		fixture = new OfxHttpMessage(new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX></OFX>");
		n = fixture.cloneSignonMessageSet();
		assertNull(n);
	}

	@Test
	public void testRemoveBillpayMessageSet() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><TEST>x<BILLPAYMSGSRQV1><FOO>BAR</BILLPAYMSGSRQV1></OFX>");
		Node n = fixture.removeBillpayMessageSet();
		assertNotNull(n);

		n = fixture.removeBillpayMessageSet();
		assertNull(n);

	}

	@Test
	public void testRemoveNode() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><TEST>x<ACCTINFO><BPACCTINFO>FOO</BPACCTINFO></ACCTINFO>"
						+ "<ACCTINFO><BPACCTINFO>BAR</BPACCTINFO></ACCTINFO></OFX>");
		Node n = fixture.removeNode("//ACCTINFO/BPACCTINFO");
		assertNotNull(n);
		assertTrue(fixture.getBody().indexOf("BPACCTINFO") > 0);

		n = fixture.removeNode("//ACCTINFO/BPACCTINFO");
		assertNotNull(n);
		assertFalse(fixture.getBody().indexOf("BPACCTINFO") > 0);

		n = fixture.removeNode("//ACCTINFO/BPACCTINFO");
		assertNull(n);
		assertFalse(fixture.getBody().indexOf("BPACCTINFO") > 0);
	}

	@Test
	public void testRemoveAllInstancesOfNode() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><TEST>x<ACCTINFO><BPACCTINFO>FOO</BPACCTINFO></ACCTINFO>"
						+ "<ACCTINFO><BPACCTINFO>BAR</BPACCTINFO></ACCTINFO></OFX>");
		fixture.removeAllInstancesOfNode("//ACCTINFO/BPACCTINFO");
		assertFalse(fixture.getBody().indexOf("BPACCTINFO") > 0);
	}

	@Test
	public void testRemoveAllInstancesOfNode2() {
		// Sample message from production
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><SIGNUPMSGSRSV1><ACCTINFOTRNRS><ACCTINFORS>"
						+ "<DTACCTUP>20100304000000[-5:EST]<ACCTINFO><DESC>ADVANTAGE53<BANKACCTINFO><BANKACCTFROM>"
						+ "<BANKID>1234<ACCTID>55555<ACCTTYPE>CHECKING</BANKACCTFROM><SUPTXDL>Y<XFERSRC>Y"
						+ "<XFERDEST>Y<SVCSTATUS>ACTIVE</BANKACCTINFO><BPACCTINFO><BANKACCTFROM>"
						+ "<BANKID>1234<ACCTID>55555<ACCTTYPE>CHECKING</BANKACCTFROM>"
						+ "<SVCSTATUS>ACTIVE</BPACCTINFO></ACCTINFO><ACCTINFO><DESC>RELATIONSHIP SAVINGS 309"
						+ "<BANKACCTINFO><BANKACCTFROM><BANKID>1234<ACCTID>66666<ACCTTYPE>SAVINGS</BANKACCTFROM>"
						+ "<SUPTXDL>Y<XFERSRC>Y<XFERDEST>Y<SVCSTATUS>ACTIVE</BANKACCTINFO><BPACCTINFO><BANKACCTFROM>"
						+ "<BANKID>1234<ACCTID>66666<ACCTTYPE>CHECKING</BANKACCTFROM><SVCSTATUS>ACTIVE"
						+ "</BPACCTINFO></ACCTINFO></ACCTINFORS></ACCTINFOTRNRS></SIGNUPMSGSRSV1></OFX>");
		fixture.removeAllInstancesOfNode("//ACCTINFO/BPACCTINFO");
		assertFalse(fixture.getBody().indexOf("BPACCTINFO") > 0);
		assertEquals(
				"OFXHEADER:100\r\n\r\n<OFX><SIGNUPMSGSRSV1><ACCTINFOTRNRS><ACCTINFORS>"
						+ "<DTACCTUP>20100304000000[-5:EST]<ACCTINFO><DESC>ADVANTAGE53"
						+ "<BANKACCTINFO><BANKACCTFROM><BANKID>1234<ACCTID>55555<ACCTTYPE>CHECKING"
						+ "</BANKACCTFROM><SUPTXDL>Y<XFERSRC>Y<XFERDEST>Y<SVCSTATUS>ACTIVE</BANKACCTINFO>"
						+ "</ACCTINFO><ACCTINFO><DESC>RELATIONSHIP SAVINGS 309<BANKACCTINFO>"
						+ "<BANKACCTFROM><BANKID>1234<ACCTID>66666<ACCTTYPE>SAVINGS</BANKACCTFROM>"
						+ "<SUPTXDL>Y<XFERSRC>Y<XFERDEST>Y<SVCSTATUS>ACTIVE</BANKACCTINFO>"
						+ "</ACCTINFO></ACCTINFORS></ACCTINFOTRNRS></SIGNUPMSGSRSV1></OFX>",
				fixture.getBody());
	}

	@Test
	public void testExtractBillpayResponseMessageSet() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><BILLPAYMSGSRSV1><FOO>BAR<BAZ>BIF</BILLPAYMSGSRSV1></OFX>");
		Node n = fixture.extractBillpayResponseMessageSet();
		assertNotNull(n);

		fixture = new OfxHttpMessage(new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX></OFX>");
		n = fixture.extractBillpayResponseMessageSet();
		assertNull(n);
	}

	@Test
	public void testAppendTag() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><BILLPAYMSGSRSV1><FOO>BAR</BILLPAYMSGSRSV1><BAZ>BIF</OFX>");
		assertEquals(
				"<OFX><BILLPAYMSGSRSV1><FOO>BAR</BILLPAYMSGSRSV1><BAZ>BIF</OFX>",
				fixture.getOfxBodyAsString());
		fixture.appendTag(fixture.getNode("/OFX"), "TAG", "YourIt");
		assertEquals(
				"<OFX><BILLPAYMSGSRSV1><FOO>BAR</BILLPAYMSGSRSV1><BAZ>BIF<TAG>YourIt</OFX>",
				fixture.getOfxBodyAsString());
	}

	@Test
	public void testAppendMessageSet() {
		OfxHttpMessage fixture = new OfxHttpMessage(
				new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><BILLPAYMSGSRSV1><FOO>BAR</BILLPAYMSGSRSV1><BAZ>BIF</OFX>");

		fixture.appendMessageSet(fixture.getNode("//FOO"));
		assertEquals(
				"<OFX><BILLPAYMSGSRSV1><FOO>BAR</BILLPAYMSGSRSV1><BAZ>BIF<FOO>BAR</OFX>",
				fixture.getOfxBodyAsString());
	}

	@Test
	public void testReplaceAll() {
		OfxHttpMessage fixture = new OfxHttpMessage(new LinkedList<Header>(),
				"OFXHEADER:100\n\n<OFX><FOO>VAL1<BAR>B1<FOO>VAL2<C>C1<BAR>B2<FOO>VAL3</OFX>");

		fixture.replaceAll("//FOO/text()", "NEW");
		assertEquals("<OFX><FOO>NEW<BAR>B1<FOO>NEW<C>C1<BAR>B2<FOO>NEW</OFX>",
				fixture.getOfxBodyAsString());

		try {
			fixture.replaceAll("//b/preceding-sibling::flark(text())", "NA");
			fail("Did not throw expected exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Invalid XPath"));
		}
	}

	@Test
	public void testElementRequiresClosingTag() {
		assertTrue(OfxHttpMessage.elementRequiresClosingTag("MSGBODY"));
		assertTrue(OfxHttpMessage.elementRequiresClosingTag("EXTDPMTDSC"));
		assertFalse(OfxHttpMessage.elementRequiresClosingTag("SOMEOTHERTAG"));
		assertFalse(OfxHttpMessage.elementRequiresClosingTag(null));
	}

}
