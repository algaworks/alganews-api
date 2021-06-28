package com.algaworks.alganews.common.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Slugfier {
	
	private Slugfier() {
	
	}
	
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	
	public static String slugify(String text) {
		if (text == null) {
			return null;
		}
		String nowhitespace = WHITESPACE.matcher(text).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}
	
}
