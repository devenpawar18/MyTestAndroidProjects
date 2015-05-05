/* Copyright 2011 Fifth Third Bank. All rights reserved. */
/**
 * 
 */
package com.fifththird.ofx.proxy.parser;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Base class for SAX handlers that deal with OFX messages. Most subclasses will
 * want to implement the {@link #endDocument()} method, and do something with
 * the list of read tags ({@link #getTags()}).
 */
public abstract class OfxSaxHandlerSupport extends DefaultHandler {

	protected Logger log = Logger.getLogger(this.getClass());

	private List<Tag> tags;

	protected Tag currentTag;

	/**
	 * Override in subclasses to use custom Tag subclasses
	 */
	protected Tag createTag(String name) {
		return new Tag(name);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String data = new String(ch, start, length);
		log.debug("Character data: " + data);
		if (currentTag != null) {
			currentTag.appendContent(data);
		} else {
			// Character data before first tag,
			// Create a 'dummy' element to hold it
			currentTag = createTag(null);
			currentTag.content = data;
			tags.add(currentTag);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		log.debug("Start Document");
		tags = new LinkedList<Tag>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (log.isDebugEnabled()) {
			log.debug("Start Element: " + localName);
		}
		currentTag = createTag(localName);
		tags.add(currentTag);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (log.isDebugEnabled()) {
			log.debug("End Element: " + localName);
		}
	}

	/**
	 * Returns the list of accumulated {@link Tag}s, in the order that they were
	 * read.
	 */
	protected List<Tag> getTags() {
		return tags;
	}

	public class Tag {
		private String name;
		private String content = "";

		public Tag(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void appendContent(String content) {
			if (this.content == null) {
				setContent(content);
			} else {
				this.content += content;
			}
		}

	}

}
