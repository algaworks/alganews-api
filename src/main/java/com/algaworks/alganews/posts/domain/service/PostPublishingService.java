package com.algaworks.alganews.posts.domain.service;

import javax.transaction.Transactional;

import com.algaworks.alganews.common.domain.BusinessException;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.posts.domain.model.Post;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostPublishingService {
	
	private final PostCrudService postCrudService;
	
	@Transactional
	public void publish(Long id) {
		Post post = postCrudService.findByIdFetchTagsAndEditorOrFail(id);
		if (post.isPublished()) {
			throw new BusinessException("Post já está publicado");
		}
		post.publish();
	}
	
	@Transactional
	public void unpublish(Long id) {
		Post post = postCrudService.findByIdFetchTagsAndEditorOrFail(id);
		if (!post.isPublished()) {
			throw new BusinessException("Post não está publicado");
		}
		post.unpublish();
	}
	
}
