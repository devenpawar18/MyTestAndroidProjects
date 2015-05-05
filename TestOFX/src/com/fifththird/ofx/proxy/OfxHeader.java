/* Copyright 2011 Fifth Third Bank. All rights reserved. */
/**
 * 
 */
package com.fifththird.ofx.proxy;

import org.apache.commons.httpclient.NameValuePair;

/**
 * Represents a single OFX header. An OFX header is a name/value pair
 * (NAME:VALUE) that is present in the HTTP message body before the OFX SGML
 * message document. Headers are separated from the message document by a single
 * blank line (according to the spec (section 2.2), but I've seen two blank
 * lines in practice).
 */
public class OfxHeader extends NameValuePair {

	public OfxHeader(String name, String value) {
		super(name, value);
	}

	@Override
	public String toString() {
		return ((null == getName() ? "" : getName()) + ":" + (null == getValue() ? ""
				: getValue()));
	}

}
