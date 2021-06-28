package com.algaworks.alganews.common.util;

public class SystemEnvUtil {
	
	private SystemEnvUtil() {
	
	}
	
	public static Integer getEnvOrDefault(String env, int defaultValue){
		String envVariable = System.getenv(env);
		
		if (envVariable == null) {
			return defaultValue;
		}
		
		try {
			return Integer.parseInt(envVariable);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
}
