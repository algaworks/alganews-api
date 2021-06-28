package com.algaworks.alganews.common.util;

public class FileNameAppender {
	
	private FileNameAppender() {
	
	}
	
	public static String appendSuffix(String fileName, String suffix) {
		String newFileName = "";
		
		int indexOfDot = fileName.lastIndexOf('.');
		
		if (indexOfDot != -1) {
			newFileName = fileName.substring(0, indexOfDot);
			newFileName += suffix;
			newFileName += fileName.substring(indexOfDot);
		} else {
			newFileName = fileName + suffix;
		}
		
		return newFileName;
	}
	
}
