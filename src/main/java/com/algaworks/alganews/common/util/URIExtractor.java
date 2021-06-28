package com.algaworks.alganews.common.util;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class URIExtractor {
	
	private URIExtractor() {
	
	}
	
	public static String extractFileName(URI uri) {
		if (uri == null) {
			return null;
		}
		return extractFileName(Paths.get(uri.getPath()));
	}
	
	public static String extractFileName(Path path) {
		if (path == null) {
			return null;
		}
		return path.getFileName().toString();
	}
	
	public static String extractFileName(String uri) {
		if (StringUtils.isEmpty(uri)) {
			return null;
		}
		
		String fileName = null;
		Pattern pattern = Pattern.compile("[^/]*$");
		Matcher matcher = pattern.matcher(uri);
		
		if (matcher.find()) {
			fileName = matcher.group(0);
		}
		
		return fileName;
	}
	
}
