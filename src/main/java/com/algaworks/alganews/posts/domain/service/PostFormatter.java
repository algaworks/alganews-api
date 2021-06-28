package com.algaworks.alganews.posts.domain.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Set;

import static com.algaworks.alganews.common.util.Slugfier.slugify;

@Component
@AllArgsConstructor
public class PostFormatter {
	
	private final PostBodyExtractor bodyExtractor;
	private final PostBodyImageService postBodyImageService;
	
	private static final String TITLE_HASH_FORMAT = "%s-%s";
	
	public String applyPermanentUrls(String postBody) {
		Set<URI> newBodyImages = bodyExtractor.getImagesByHost(postBody, postBodyImageService.getUploadUri());
		return applyDownloadUrlInImages(postBody, newBodyImages);
	}
	
	public String applyDownloadUrlInImages(String postBody, Set<URI> bodyImages) {
		if (bodyImages.isEmpty())
			return postBody;
		
		for (URI tmpUrl : bodyImages)
			postBody = replaceTmpUrlWithPermanentUrl(postBody, tmpUrl);
		
		return postBody;
	}
	
	private String replaceTmpUrlWithPermanentUrl(String postBody, URI tmpUrl) {
		URI downloadUrl = postBodyImageService.getPostBodyImageDownloadUri(tmpUrl);
		return postBody.replace(tmpUrl.toString(), downloadUrl.toString());
	}
	
	public String generateUniqueSlug(String title) {
		return slugify(String.format(TITLE_HASH_FORMAT, title, RandomStringUtils.randomAlphanumeric(5)));
	}
}