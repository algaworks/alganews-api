package com.algaworks.alganews.posts.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.api.model.PostWithEarningsModel;
import com.algaworks.alganews.posts.domain.model.Post;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostWithEarningsAssembler {
	
	private final ModelMapper modelMapper;
	
	public PostWithEarningsModel toModel(Post post) {
		return modelMapper.map(post, PostWithEarningsModel.class);
	}
	
	public List<PostWithEarningsModel> toCollectionModel(List<Post> posts) {
		return posts.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
