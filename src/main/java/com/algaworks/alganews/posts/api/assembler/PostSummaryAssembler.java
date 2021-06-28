package com.algaworks.alganews.posts.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.alganews.users.api.assembler.EditorSummaryAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.api.model.PostSummaryModel;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.security.AlgaSecurity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostSummaryAssembler {
	
	private final ModelMapper modelMapper;
	private final AlgaSecurity algaSecurity;
	private final EditorSummaryAssembler editorSummaryAssembler;
	
	public PostSummaryModel toModel(Post post) {
		PostSummaryModel model = modelMapper.map(post, PostSummaryModel.class);
		
		model.setCanBePublished(algaSecurity.canPublishPost(post));
		model.setCanBeUnpublished(algaSecurity.canPostBeUnpublished(post));
		model.setCanBeDeleted(algaSecurity.canPostBeDeleted(post));
		model.setCanBeEdited(algaSecurity.canEditPost(post));
		model.setEditor(editorSummaryAssembler.toModel(post.getEditor()));
		
		return model;
	}
	
	public List<PostSummaryModel> toCollectionModel(List<Post> posts) {
		return posts.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
