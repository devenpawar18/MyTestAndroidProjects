/* Copyright 2011 Fifth Third Bank. All rights reserved. */
package com.fifththird.ofx.proxy.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Generates a DOM ({@link Document} from an OFX document
 */
public class OfxDomParser {

	private static final Logger log = Logger.getLogger(OfxDomParser.class);

	/**
	 * Parses an OFX SGML message into a {@link Document} object.
	 */
	public static Document parse(String input) {
		return parse(new StringReader(input));
	}

	/**
	 * Parses an OFX SGML message into a {@link Document} object.
	 */
	public static Document parse(Reader input) {
		DomBuildingContentHandler ch = null;
		try {
			ch = new DomBuildingContentHandler();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Error getting Document instance", e);
		}
		OfxSaxParser parser = new OfxSaxParser(input, ch);
		try {
			parser.parse();
			return ch.getDocument();
		} catch (SAXException e) {
			log.error("Error parsing input message", e);
			return null;
		}
	}

	static class DomBuildingContentHandler extends OfxSaxHandlerSupport {

		private Document doc;

		public DomBuildingContentHandler() throws ParserConfigurationException {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
		}

		public Document getDocument() {
			return doc;
		}

		@Override
		// Overriden to use HierarchicalTag for tracking document structure
		protected Tag createTag(String name) {
			return new HierarchicalTag(name);
		}

		@Override
		public void endDocument() throws SAXException {
			log.debug("End Document");

			// Establish document structure (parent-child relationships)
			setDocumentStructure();

			// Generate Document. First tag is the doc root
			if (getTags().size() > 0) {
				HierarchicalTag root = (HierarchicalTag) getTags().get(0);
				addTagElement(root, doc);
			}
		}

		private void addTagElement(HierarchicalTag tag, Node parent) {
			// Create the element and add it's text contents (if any)
			Element elem = doc.createElement(tag.getName());
			if (StringUtils.isNotEmpty(tag.getContent())) {
				elem.appendChild(doc.createTextNode(tag.getContent()));
			}
			parent.appendChild(elem);

			// Add children recursively
			List<HierarchicalTag> kids = tag.getChildren();
			for (HierarchicalTag kid : kids) {
				addTagElement(kid, elem);
			}
		}

		private void setDocumentStructure() {
			// Iterate stack, setting parents as needed
			List<Tag> tags = getTags();
			int idx = -1;
			for (Tag t : tags) {
				idx++;
				HierarchicalTag tag = (HierarchicalTag) t;
				String name = tag.getName();
				// Check for a tag closing an aggregate
				if (name != null && name.startsWith("/") && tags.size() > 1) {
					// Go back through the tags to find the opening tag,
					// keeping track of possible children
					LinkedList<HierarchicalTag> children = new LinkedList<HierarchicalTag>();
					String targetTag = name.substring(1);
					ListIterator<Tag> it = tags.listIterator(idx);
					HierarchicalTag parent = null;
					while (it.hasPrevious()) {
						HierarchicalTag e = (HierarchicalTag) it.previous();
						if (e.getName().equals(targetTag)) {
							parent = e;
							break;
						} else {
							children.add(e);
						}
					}
					// Could check for mismatched closing tag here

					// Establish parent-child relationships.
					// Reverse list of possible children to restore same order
					// as in original document
					Collections.reverse(children);
					for (HierarchicalTag possibleChild : children) {
						// If the tag doesn't already have a parent and it's not
						// a closing tag, then set it's parent and add it to the
						// parent's list of children
						if (possibleChild.getParent() == null
								&& !possibleChild.getName().startsWith("/")) {
							possibleChild.setParent(parent);
							parent.addChild(possibleChild);
						}
					}
				}
			}
		}

		public class HierarchicalTag extends Tag {
			private Tag parent;
			private List<HierarchicalTag> children;

			public HierarchicalTag(String name) {
				super(name);
				children = new LinkedList<HierarchicalTag>();
			}

			public Tag getParent() {
				return parent;
			}

			public void setParent(Tag parent) {
				this.parent = parent;
			}

			public List<HierarchicalTag> getChildren() {
				return children;
			}

			public void addChild(HierarchicalTag child) {
				children.add(child);
			}
		}

	}
}
