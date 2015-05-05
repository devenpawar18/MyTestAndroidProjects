/* Copyright 2011 Fifth Third Bank. All rights reserved. */
/**
 * 
 */
package com.fifththird.ofx.proxy;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;

/**
 * Encapsulates an HTTP message
 */
public class HttpMessage
{
	private List<Header> httpHeaders = new LinkedList<Header>();
	private String body;
	private int statusCode;

	public HttpMessage(PostMethod post) throws IOException
	{
		this.body = post.getResponseBodyAsString();
		this.statusCode = post.getStatusCode();
		for (Header header : post.getResponseHeaders())
		{
			httpHeaders.add(header);
		}
	}

	public HttpMessage(List<Header> httpHeaders, String body, int statusCode)
	{
		this.httpHeaders = httpHeaders;
		this.body = body;
		this.statusCode = statusCode;
	}

	public HttpMessage(List<Header> httpHeaders, String body)
	{
		this.httpHeaders = httpHeaders;
		this.body = body;
		this.statusCode = 200;
	}

	/**
	 * Removes a named HTTP header. If multiple headers exist with the same name, all will be
	 * removed.
	 * 
	 * @param name
	 *            Name of the header to remove
	 * @return Removed header or null if requested header was not found. If multiple headers exist
	 *         with the same name, only the last removed header will be returned.
	 */
	public Header removeHttpHeader(String name)
	{
		if (httpHeaders == null)
		{
			return null;
		}
		Header removedHeader = null;
		Iterator<Header> itHeaders = httpHeaders.iterator();
		while (itHeaders.hasNext())
		{
			Header header = itHeaders.next();
			if (StringUtils.equalsIgnoreCase(name, header.getName()))
			{
				itHeaders.remove();
				removedHeader = header;
			}
		}
		return removedHeader;
	}

	public List<Header> getHeaders()
	{
		return httpHeaders;
	}

	public void setHeaders(List<Header> headers)
	{
		this.httpHeaders = headers;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

}
