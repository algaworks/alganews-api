package com.algaworks.alganews.common.util;

import org.apache.commons.lang3.StringUtils;

public class MarkdownAnalyser {
	
	private MarkdownAnalyser() {
	
	}
	
	public static int countWordsIgnoringTags(String text) {
		if (StringUtils.isBlank(text)) {
			return 0;
		}
		
		// Comments
		text = text.replaceAll("<!--(.*?)-->", "");
		// Tabs to spaces
		text = text.replace("\t", " ");
		// More than 1 space to 4 spaces
		text = text.replaceAll("[ ]{2,}", " ");
		// Footnotes
		text = text.replaceAll("^\\[[^]]*\\][^(].*", "");
		// Indented blocks of code
		text = text.replaceAll("^( {4,}[^-*]).*", "");
		// Custom header IDs
		text = text.replaceAll("\\{#.*}", "");
		// Replace newlines with spaces for uniform handling
		text = text.replace("\n", " ");
		// Remove images
		text = text.replaceAll("!\\[[^\\]]*\\]\\([^)]*\\)", "");
		// Remove HTML tags
		text = text.replaceAll("</?[^>]*>", "");
		// Remove special characters
		text = text.replaceAll("[#*`~\\-–^=<>+|/:]", "");
		// Remove footnote references
		text = text.replaceAll("\\[[0-9]*\\]", "");
		// Remove enumerations
		text = text.replaceAll("[0-9#]*\\.", "");
		// Remove duplicated chars
		text = text.replaceAll("\\s{2,}", " ").trim();
		// Replace whitespace with separator
		text = text.replace(" ", "|");
		// replace multiples separators
		text = text.replace("\\|{2,99}/gi", "|");
		// Slip words and ignore the separator
		
		return text.split("\\|", -1).length;
	}
	
}
