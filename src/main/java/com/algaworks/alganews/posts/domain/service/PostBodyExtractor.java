package com.algaworks.alganews.posts.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class PostBodyExtractor {
	
	public Set<URI> getImagesByHost(String body, URI uploadHostUrl) {
		Set<URI> postBodyImages = new HashSet<>();
		
		Pattern regularExpression = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
		Matcher matcher = regularExpression.matcher(body);
		
		while (matcher.find()) {
			URI fileUrl = extractUrl(matcher);
			if (hasSameHostAndPath(fileUrl, uploadHostUrl))
				postBodyImages.add(fileUrl);
		}
		
		return postBodyImages;
	}
	
	private URI extractUrl(Matcher matcher) {
		String rawUrl = matcher.group(2);
		return URI.create(rawUrl);
	}
	
	private boolean hasSameHostAndPath(URI fileUrl, URI uploadHostUrl) {
		return fileUrl.toString().startsWith(uploadHostUrl.toString());
	}
	
}
