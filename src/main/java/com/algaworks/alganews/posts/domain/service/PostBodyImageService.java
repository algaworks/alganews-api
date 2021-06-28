package com.algaworks.alganews.posts.domain.service;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.ImageStorageService;
import com.algaworks.alganews.common.util.URIExtractor;
import com.algaworks.alganews.posts.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


@Service
@AllArgsConstructor
public class PostBodyImageService {
	
	private final PostBodyExtractor postBodyExtractor;
	private final ImageStorageService imageStorageService;
	private final StorageProperties storageProperties;
	
	public void uploadBodyImages(Post post) {
		verifyIfImagesExists(post);
		uploadImages(post);
	}
	
	public void uploadBodyImages(Post updatedPost, Post existingPost) {
		verifyIfImagesExists(updatedPost);
		uploadImages(updatedPost, existingPost);
	}
	
	public URI getUploadUri() {
		return imageStorageService.getUploadUri();
	}
	
	public URI getPostBodyImageDownloadUri(URI bodyImageUri) {
		if (bodyImageUri == null) return null;
		
		String fileName = URIExtractor.extractFileName(bodyImageUri.getPath());
		
		String rawPath = String.format("%s/%s/%s",
				imageStorageService.getDownloadUri(),
				storageProperties.getPostBodyImageFolder(),
				fileName);
		
		return URI.create(rawPath);
	}
	
	private Path generatePermanentStoragePathForPostBodyImage(URI bodyImageUri) {
		if (bodyImageUri == null) return null;
		return Paths.get(String.format("%s/%s", storageProperties.getPostBodyImageFolder(),
				URIExtractor.extractFileName(bodyImageUri.getPath())));
	}
	
	private void verifyIfImagesExists(Post post) {
		Set<URI> addedImages = extractTemporaryBodyImages(post);
		addedImages.forEach(bodyImage -> imageStorageService.verifyIfImageExists(Paths.get(bodyImage.getPath())));
	}
	
	private void uploadImages(Post post) {
		Set<URI> bodyImages = extractTemporaryBodyImages(post);
		bodyImages.forEach(bodyImageUri ->
				imageStorageService.makePermanent(generatePermanentStoragePathForPostBodyImage(bodyImageUri)));
	}
	
	private void uploadImages(Post updatedPost, Post existingPost) {
		Set<URI> newImages = extractTemporaryBodyImages(updatedPost);
		Set<URI> removedImages = getRemovedImages(updatedPost, existingPost);
		
		makePermanent(newImages);
		delete(removedImages);
	}
	
	private Set<URI> getRemovedImages(Post updatedPost, Post existingPost) {
		Set<URI> unchangedImages = extractPermanentBodyImages(updatedPost);
		Set<URI> originalImages = extractPermanentBodyImages(existingPost);
		Set<URI> removedImages = new HashSet<>(originalImages);
		removedImages.removeAll(unchangedImages);
		return removedImages;
	}
	
	private Set<URI> extractTemporaryBodyImages(Post post) {
		return postBodyExtractor.getImagesByHost(post.getBody(), imageStorageService.getUploadUri());
	}
	private Set<URI> extractPermanentBodyImages(Post post) {
		return postBodyExtractor.getImagesByHost(post.getBody(), imageStorageService.getDownloadUri());
	}
	
	private void delete(Set<URI> removedImageUris) {
		removedImageUris.forEach(removedImageUri ->
				imageStorageService.removeFile(generatePermanentStoragePathForPostBodyImage(removedImageUri)));
	}
	
	private void makePermanent(Set<URI> addedImageUris) {
		addedImageUris.stream().map(this::generatePermanentStoragePathForPostBodyImage)
				.forEach(imageStorageService::makePermanent);
	}
	
}
