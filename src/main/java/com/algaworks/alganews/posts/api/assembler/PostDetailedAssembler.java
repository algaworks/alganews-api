package com.algaworks.alganews.posts.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.alganews.users.api.assembler.EditorSummaryAssembler;
import com.algaworks.alganews.users.api.assembler.UserMinimalModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.api.model.PostDetailedModel;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.security.AlgaSecurity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostDetailedAssembler {
	
	private final ModelMapper modelMapper;
	private final AlgaSecurity algaSecurity;
	private final EditorSummaryAssembler editorSummaryAssembler;
	private final UserMinimalModelAssembler userMinimalModelAssembler;
	
	public PostDetailedModel toModel(Post post) {
		PostDetailedModel model = modelMapper.map(post, PostDetailedModel.class);
		
		if (!algaSecurity.canViewPostEarnings(post)) {
			model.setEarnings(null);
		}
		model.setCanBePublished(algaSecurity.canPublishPost(post));
		model.setCanBeUnpublished(algaSecurity.canPostBeUnpublished(post));
		model.setCanBeDeleted(algaSecurity.canPostBeDeleted(post));
		model.setEditor(editorSummaryAssembler.toModel(post.getEditor()));
		model.setCanBeEdited(algaSecurity.canEditPost(post));
		model.setUpdatedBy(userMinimalModelAssembler.toModel(post.getUpdatedBy()));
		
		return model;
	}
	
	public List<PostDetailedModel> toCollectionModel(List<Post> posts) {
		return posts.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
