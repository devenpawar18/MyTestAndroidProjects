/* Copyright 2011 Fifth Third Bank. All rights reserved. */
/**
 * 
 */
package com.fifththird.ofx.proxy.parser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class OfxPrettyPrint {

	private static final Logger log = Logger.getLogger(OfxPrettyPrint.class);

	public enum MaskType {
		NOT_DISPLAYED, LAST4
	};

	protected static final Map<String, MaskType> maskedTags = new HashMap<String, MaskType>();
	static {
		maskedTags.put("USERPASS", MaskType.NOT_DISPLAYED);
	}

	public static String prettyPrint(String input) {
		PrettyPrintContentHandler ch = new PrettyPrintContentHandler();
		OfxSaxParser parser = new OfxSaxParser(new StringReader(input), ch);
		try {
			parser.parse();
			return ch.getOutput();
		} catch (SAXException e) {
			log.error("Error parsing input message", e);
			return null;
		}
	}

	static class PrettyPrintContentHandler extends OfxSaxHandlerSupport {

		private static final int INDENT_SPACES = 3;

		private String output = null;

		public String getOutput() {
			return output;
		}

		@Override
		// Overriden to use IndentedTag for tracking indent
		protected Tag createTag(String name) {
			return new IndentedTag(name);
		}

		@Override
		public void endDocument() throws SAXException {
			log.debug("End Document");

			// Iterate stack, adding indent as needed
			List<Tag> tags = getTags();
			int idx = -1;
			for (Tag t : tags) {
				idx++;
				IndentedTag tag = (IndentedTag) t;
				String name = tag.getName();
				// Check for a tag closing an aggregate
				if (name != null && name.startsWith("/") && tags.size() > 1) {
					// Go back through the tags, incrementing the indent level
					// until we hit an opening tag matching this closing tag
					String targetTag = name.substring(1);
					ListIterator<Tag> it = tags.listIterator(idx);
					while (it.hasPrevious()) {
						IndentedTag e = (IndentedTag) it.previous();
						if (e.getName().equals(targetTag)) {
							break;
						} else {
							e.indent++;
						}
					}
				}
			}

			// Generate output
			StringBuffer out = new StringBuffer(50 * tags.size());
			for (Tag t : tags) {
				IndentedTag tag = (IndentedTag) t;
				addIndent(out, tag.indent);
				String name = tag.getName();
				if (name != null && name.length() > 0) {
					// Output tag
					out.append('<').append(name).append('>');
				}
				if (tag.getContent() != null) {
					// Output tag contents, masking and including a newline as
					// needed
					String contents = getMaskedTagContents(tag);
					if (contents == null) {
						out.append('\n');
					} else {
						out.append(contents);
						if (!contents.endsWith("\n")) {
							out.append('\n');
						}
					}
				} else {
					// No tag content
					out.append('\n');
				}
			}

			// Dump to string
			output = out.toString();
		}

		/**
		 * Add spaces as appropriate for indent level
		 */
		private void addIndent(StringBuffer sb, int level) {
			for (int i = 0; i < level; i++) {
				for (int j = 0; j < INDENT_SPACES; j++) {
					sb.append(' ');
				}
			}
		}

		private String getMaskedTagContents(Tag tag) {
			String value = tag.getContent();
			MaskType maskType = maskedTags.get(tag.getName());
			if (maskType == null) {
				return value;
			}
			switch (maskType) {
			case NOT_DISPLAYED:
				value = "[Not Displayed]";
				break;
			case LAST4:
				int maskCount = value == null ? 0 : Math.max(
						value.length() - 4, 0);
				value = StringUtils.repeat("x", maskCount)
						+ StringUtils.right(value, 4);
				break;
			default:
				// No masking. Don't do anything, just return the tag's value
				break;
			}
			return value;
		}

		public class IndentedTag extends Tag {
			public int indent;

			public IndentedTag(String name) {
				super(name);
			}
		}

	}
}
