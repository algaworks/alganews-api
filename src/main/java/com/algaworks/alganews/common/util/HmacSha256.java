package com.algaworks.alganews.common.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha256 {
	
	private HmacSha256() {
	
	}
	
	public static String sign(String secret, String data) {
		try {
			Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			sha256Hmac.init(secretKey);
			byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64Encoder.encode(signedBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException("Erro assinando com HMAC SHA256", e);
		}
	}
	
}
