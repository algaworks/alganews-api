package com.algaworks.alganews.posts.domain.service;

import com.algaworks.alganews.util.ResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostBodyExtractorTest {
	
	private PostBodyExtractor postBodyExtractor;
	
	@BeforeEach
	void setup() {
		 postBodyExtractor = new PostBodyExtractor();
	}
	
	@Test
	void givenAnHost_returnImagesBelongToHost() {
		String originalPostBody = ResourceUtils.getContentFromResource("/md/post1-original.md");
		String updatedPostBody = ResourceUtils.getContentFromResource("/md/post1-tmp.md");
		
		URI uploadHost = URI.create("http://localhost:8080/upload/tmp");
		URI downloadHost = URI.create("http://localhost:8080/alganews/posts");
		
		Set<URI> originalImages = createOriginalImagesList();
		Set<URI> addedImages = createAddedImagesList();
		Set<URI> ignoredExternalURIs = createExternalURIs();
		
		Set<URI> extractedAddedImages = postBodyExtractor.getImagesByHost(updatedPostBody, uploadHost);
		Set<URI> extractedOriginalImages = postBodyExtractor.getImagesByHost(originalPostBody, downloadHost);
		
		assertTrue(extractedAddedImages.containsAll(addedImages));
		assertTrue(extractedOriginalImages.containsAll(originalImages));
		
		assertFalse(extractedAddedImages.containsAll(ignoredExternalURIs));
		assertFalse(extractedOriginalImages.containsAll(ignoredExternalURIs));
	}
	
	private HashSet<URI> createExternalURIs() {
		return new HashSet<>() {{
			add(URI.create("http://www.meulinkexterno.com/foto.png"));
			add(URI.create("http://www.google.com/logo.png"));
		}};
	}
	
	private HashSet<URI> createAddedImagesList() {
		return new HashSet<>() {{
			add(URI.create("http://localhost:8080/upload/tmp/1eef96842735.png"));
			add(URI.create("http://localhost:8080/upload/tmp/1eef96842755.png"));
		}};
	}
	
	private HashSet<URI> createOriginalImagesList() {
		return new HashSet<>() {{
			add(URI.create("http://localhost:8080/alganews/posts/1eef96842739.png"));
			add(URI.create("http://localhost:8080/alganews/posts/1eef96842731.png"));
			add(URI.create("http://localhost:8080/alganews/posts/1eef96842730.png"));
		}};
	}
	
}