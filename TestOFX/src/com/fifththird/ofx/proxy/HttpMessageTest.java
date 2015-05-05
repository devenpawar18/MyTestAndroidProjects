/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

public class HttpMessageTest {

	@Test
	public void testConstructor() throws Exception {
		PostMethod post = mock(PostMethod.class);
		when(post.getResponseBodyAsString()).thenReturn("body");
		when(post.getStatusCode()).thenReturn(300);
		when(post.getResponseHeaders()).thenReturn(
				new Header[] { new Header("foo", "bar"),
						new Header("abc", "xyz") });
		HttpMessage fixture = new HttpMessage(post);
		assertEquals("body", fixture.getBody());
		assertEquals(300, fixture.getStatusCode());
		assertEquals(2, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("foo", "bar")));

		List<Header> lHeaders = new LinkedList<Header>();
		lHeaders.add(new Header("foo", "bar"));
		fixture = new HttpMessage(lHeaders, "body", 301);
		assertEquals("body", fixture.getBody());
		assertEquals(301, fixture.getStatusCode());
		assertEquals(1, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("foo", "bar")));

		fixture = new HttpMessage(lHeaders, "body");
		assertEquals("body", fixture.getBody());
		assertEquals(200, fixture.getStatusCode());
		assertEquals(1, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("foo", "bar")));
	}

	@Test
	public void testSetters() {
		List<Header> lHeaders = new LinkedList<Header>();
		lHeaders.add(new Header("foo", "bar"));
		HttpMessage fixture = new HttpMessage(lHeaders, "body", 301);
		assertEquals("body", fixture.getBody());
		assertEquals(301, fixture.getStatusCode());
		assertEquals(1, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("foo", "bar")));

		List<Header> newHeaders = new LinkedList<Header>();
		newHeaders.add(new Header("asdf", "243"));
		fixture.setBody("newBody");
		fixture.setHeaders(newHeaders);
		fixture.setStatusCode(500);
		assertEquals("newBody", fixture.getBody());
		assertEquals(500, fixture.getStatusCode());
		assertEquals(1, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("asdf", "243")));
	}

	@Test
	public void testRemoveHeader() {
		List<Header> lHeaders = new LinkedList<Header>();
		lHeaders.add(new Header("h1", "v1"));
		lHeaders.add(new Header("h2", "v2"));
		lHeaders.add(new Header("h3", "v3"));
		HttpMessage fixture = new HttpMessage(lHeaders, "body");
		assertEquals(3, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("h1", "v1")));
		assertTrue(fixture.getHeaders().contains(new Header("h2", "v2")));
		assertTrue(fixture.getHeaders().contains(new Header("h3", "v3")));

		Header removed = fixture.removeHttpHeader("h2");
		assertEquals(new Header("h2", "v2"), removed);
		removed = fixture.removeHttpHeader("h3");
		assertEquals(new Header("h3", "v3"), removed);
		assertEquals(1, fixture.getHeaders().size());
		assertTrue(fixture.getHeaders().contains(new Header("h1", "v1")));
		assertFalse(fixture.getHeaders().contains(new Header("h2", "v2")));
		assertFalse(fixture.getHeaders().contains(new Header("h3", "v3")));
	}

	@Test
	public void testRemoveHeadersNull() {
		HttpMessage fixture = new HttpMessage(null, "body");
		assertNull(fixture.removeHttpHeader("blah"));
	}
}
