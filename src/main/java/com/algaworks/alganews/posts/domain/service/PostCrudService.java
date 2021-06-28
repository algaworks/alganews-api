package com.algaworks.alganews.posts.domain.service;

import javax.transaction.Transactional;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.common.storage.domain.StorageFileNotFoundException;
import com.algaworks.alganews.posts.domain.filter.PostFilter;
import com.algaworks.alganews.posts.domain.repository.PostSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.posts.domain.exception.PostNotFoundException;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.security.AlgaSecurity;

import lombok.AllArgsConstructor;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PostCrudService {
	
	private static final String ERROR_SHOW_ALL =
			"Você não tem permissão para utilizar esse tipo de filtro (showAll = true)";
	private static final String ERROR_SHOW_UNPUBLISHED =
			"Você não tem permissão para utilizar esse tipo de filtro (editorId = %s & showAll = true)";
	
	private final AlgaSecurity algaSecurity;
	
	private final PostRepository postRepository;
	private final PostFormatter postFormatter;
	private final PostEarningsCalculator postEarningsCalculator;
	private final PostHeaderImageService postHeaderImageService;
	private final PostBodyImageService postBodyImageService;
	
	public Post findByIdFetchTagsAndEditorOrFail(Long id) {
		Post post = postRepository.findByIdFetchTagsAndEditor(id).orElseThrow(() -> new PostNotFoundException(id));
		
		if (algaSecurity.canNotViewPost(post))
			throw new PostNotFoundException(id);
		
		return post;
	}
	
	public Page<Post> findAll(PostFilter filter, Pageable pageable) {
		validateFilter(filter);
		applyDefaults(filter);
		return postRepository.findAll(PostSpecs.usingFilter(filter), pageable);
	}
	
	private void applyDefaults(PostFilter filter) {
		if (filter.isShowAll() && algaSecurity.hasEditorRole())
			filter.setEditorId(algaSecurity.getUserId());
	}
	
	private void validateFilter(PostFilter filter) {
		if (canNotUseShowAllFilter(filter))
			throw new AccessDeniedException(ERROR_SHOW_ALL);
		
		if (canNotUseShowUnpublishedPostsFilter(filter))
			throw new AccessDeniedException(String.format(ERROR_SHOW_UNPUBLISHED, filter.getEditorId()));
	}
	
	private boolean canNotUseShowAllFilter(PostFilter filter) {
		return filter.isShowAll() && algaSecurity.getUserId() == null;
	}
	
	private boolean canNotUseShowUnpublishedPostsFilter(PostFilter filter) {
		return filter.isShowAll()
				&& algaSecurity.hasEditorRole()
				&& filter.getEditorId() != null
				&& !Objects.equals(filter.getEditorId(), algaSecurity.getUserId());
	}
	
	@Transactional
	public Post save(Post post) {
		if (post.isNew())
			return create(post);
		return edit(post);
	}
	
	@Transactional
	private Post create(Post post) {
		postRepository.detach(post);
		
		applySlug(post);
		applyEarningsIfPossible(post);
		saveAndFlush(post);
		
		uploadImages(post);
		
		formatBody(post);
		saveAndFlush(post);
		
		return post;
	}
	
	@Transactional
	private Post edit(Post post) {
		if (post.isPublished())
			throw new BusinessException("Não é possível editar um post publicado.");
		
		postRepository.detach(post);
		
		Post existingPost = findByIdFetchTagsAndEditorOrFail(post.getId());
		postRepository.detach(existingPost);
		
		if (titleHasChanged(post, existingPost))
			applySlug(post);
		
		applyEarningsIfPossible(post);
		saveAndFlush(post);
		
		updateImages(post, existingPost);
		
		formatBody(post);
		saveAndFlush(post);
		
		return post;
	}
	
	private void applySlug(Post post) {
		post.setSlug(postFormatter.generateUniqueSlug(post.getTitle()));
	}
	
	private void applyEarningsIfPossible(Post post) {
		if (post.isNotPaid())
			post.setEarnings(postEarningsCalculator.calculate(post.getBody(), algaSecurity.getAuthenticatedUserOrFail()));
	}
	
	private void saveAndFlush(Post post) {
		postRepository.save(post);
		postRepository.flush();
	}
	
	private void uploadImages(Post post) {
		try {
			postHeaderImageService.uploadHeaderImages(post);
			postBodyImageService.uploadBodyImages(post);
		} catch (StorageFileNotFoundException e) {
			log.error(e.getMessage());
			throw new BusinessException("Um ou mais arquivos não foram encontrados.", e);
		}
	}
	
	private void updateImages(Post updatedPost, Post existingPost) {
		try {
			postHeaderImageService.uploadHeaderImages(updatedPost, existingPost);
			postBodyImageService.uploadBodyImages(updatedPost, existingPost);
		} catch (StorageFileNotFoundException e) {
			log.error(e.getMessage());
			throw new BusinessException("Um ou mais arquivos não foram encontrados.", e);
		}
	}
	
	private void formatBody(Post post) {
		post.setBody(postFormatter.applyPermanentUrls(post.getBody()));
	}
	
	private boolean titleHasChanged(Post post, Post existingPost) {
		return !existingPost.getTitle().equals(post.getTitle());
	}
	
	@Transactional
	public void delete(Long id) {
		Post post = findByIdFetchTagsAndEditorOrFail(id);
		post.delete();
	}
	
	
}