package com.algaworks.alganews.posts.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.posts.api.assembler.PostDetailedAssembler;
import com.algaworks.alganews.posts.api.assembler.PostInputDisassembler;
import com.algaworks.alganews.posts.api.assembler.PostSummaryAssembler;
import com.algaworks.alganews.posts.api.model.PostDetailedModel;
import com.algaworks.alganews.posts.api.model.PostInput;
import com.algaworks.alganews.posts.api.model.PostSummaryModel;
import com.algaworks.alganews.posts.domain.filter.PostFilter;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.service.PostCrudService;
import com.algaworks.alganews.posts.domain.service.PostPublishingService;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostCrudService postCrudService;
	private final PostPublishingService postPublishingService;
	
	private final PostSummaryAssembler postSummaryAssembler;
	private final PostDetailedAssembler postDetailedAssembler;
	
	private final PostInputDisassembler postInputDisassembler;
	
	@CheckSecurity.Public
	@GetMapping
	public Page<PostSummaryModel> search(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
										 Pageable pageable, PostFilter filter) {
		Page<Post> postsPage = postCrudService.findAll(filter, pageable);
		List<PostSummaryModel> postsSummariesModel = postSummaryAssembler.toCollectionModel(postsPage.getContent());
		return new PageImpl<>(postsSummariesModel, pageable, postsPage.getTotalElements());
	}
	
	@CheckSecurity.Public
	@GetMapping("/{postId}")
	public PostDetailedModel findById(@PathVariable Long postId) {
		Post post = postCrudService.findByIdFetchTagsAndEditorOrFail(postId);
		return postDetailedAssembler.toModel(post);
	}
	
	@CheckSecurity.Posts.CanCreate
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PostDetailedModel create(@Valid @RequestBody PostInput postInput) {
		Post post = postInputDisassembler.toDomainObject(postInput);
		post = postCrudService.save(post);
		return postDetailedAssembler.toModel(post);
	}
	
	@CheckSecurity.Posts.CanEdit
	@PutMapping("/{postId}")
	public PostDetailedModel update(@PathVariable Long postId,
									@Valid @RequestBody PostInput postInput) {
		Post post = postCrudService.findByIdFetchTagsAndEditorOrFail(postId);
		postInputDisassembler.copyToDomainObject(postInput, post);
		post = postCrudService.save(post);
		return postDetailedAssembler.toModel(post);
	}
	
	@CheckSecurity.Posts.CanDelete
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long postId) {
		postCrudService.delete(postId);
	}
	
	@CheckSecurity.Posts.CanPublish
	@PutMapping("/{postId}/publishing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void publish(@PathVariable Long postId) {
		postPublishingService.publish(postId);
	}
	
	@CheckSecurity.Posts.CanUnpublish
	@DeleteMapping("/{postId}/publishing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unpublish(@PathVariable Long postId) {
		postPublishingService.unpublish(postId);
	}
	
}
