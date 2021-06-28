package com.algaworks.alganews.common.util;

import java.util.Base64;

public class Base64Encoder {
	
	private Base64Encoder() {
	
	}
	
	public static String encode(String data) {
		return encode(data.getBytes());
	}
	
	public static String encode(byte[] data) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
	}
	
	public static String decode(String data) {
		return new String(Base64.getUrlDecoder().decode(data));
	}
	
}

