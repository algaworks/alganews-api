package com.algaworks.alganews.common.util;

public class ImageContentTypeExtractor {
	
	private ImageContentTypeExtractor() {
	
	}
	
	public static String getContentTypeByExtension(String extension) {
		if (extension.equalsIgnoreCase("jpg")) {
			extension = "jpeg";
		}
		
		return "image/" + extension.toLowerCase();
	}
	
}
