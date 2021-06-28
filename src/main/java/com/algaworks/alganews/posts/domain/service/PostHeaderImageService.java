package com.algaworks.alganews.posts.domain.service;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.ImageStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.posts.domain.model.Post;

import java.net.URI;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class PostHeaderImageService {
	
	private final ImageStorageService imageStorageService;
	private final StorageProperties storageProperties;
	
	public void uploadHeaderImages(Post post) {
		verifyIfImageExists(post);
		
		if (post.hasImage())
			imageStorageService.makePermanent(generatePermanentStoragePathForPostImage(post.getImage()));
	}
	
	public void uploadHeaderImages(Post updatedPost, Post existingPost) {
		Path existingPostImagePath = generatePermanentStoragePathForPostImage(existingPost.getImage());
		Path updatePostImagePath = generatePermanentStoragePathForPostImage(updatedPost.getImage());
		
		if (imageWasRemoved(updatedPost, existingPost)) {
			imageStorageService.removeFile(existingPostImagePath);
			
		} else if (imagedWasChanged(updatedPost, existingPost)) {
			verifyIfImageExists(updatedPost);
			imageStorageService.removeFile(existingPostImagePath);
			imageStorageService.makePermanent(updatePostImagePath);
			
		} else if (imageWasAdded(updatedPost, existingPost)) {
			verifyIfImageExists(updatedPost);
			imageStorageService.makePermanent(updatePostImagePath);
		}
	}
	
	public URI getPostImageDownloadUri(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return null;
		
		return URI.create(String.format("%s/%s/%s",
				imageStorageService.getDownloadUri(),
				storageProperties.getPostImageFolder(),
				fileName));
	}
	
	private Path generatePermanentStoragePathForPostImage(String postImageName) {
		if (postImageName == null)
			return null;
		return Paths.get(String.format("%s/%s", storageProperties.getPostImageFolder(), postImageName));
	}
	
	private void verifyIfImageExists(Post post) {
		if (post.hasImage())
			imageStorageService.verifyIfImageExists(post.getImageAsPath());
	}
	
	private boolean imageWasAdded(Post post, Post existingPost) {
		return post.hasImage()
				&& existingPost.doesNotHaveImage();
	}
	
	private boolean imageWasRemoved(Post post, Post existingPost) {
		return post.doesNotHaveImage()
				&& existingPost.hasImage();
	}
	
	private boolean imagedWasChanged(Post post, Post existingPost) {
		return post.hasImage()
				&& existingPost.hasImage()
				&& imageAreNotEquals(post, existingPost);
	}
	
	private boolean imageAreNotEquals(Post post, Post existingPost) {
		return !post.getImageAsPath().getFileName().equals(existingPost.getImageAsPath().getFileName());
	}
	
}
