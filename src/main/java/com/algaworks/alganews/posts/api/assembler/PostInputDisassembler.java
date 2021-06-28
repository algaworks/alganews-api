package com.algaworks.alganews.posts.api.assembler;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.api.model.PostInput;
import com.algaworks.alganews.posts.domain.model.Post;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostInputDisassembler {

	private final ModelMapper modelMapper;
	
	public Post toDomainObject(PostInput postInput) {
		return modelMapper.map(postInput, Post.class);
	}
	
	public void copyToDomainObject(PostInput postInput, Post post) {
		//É necessário criar uma nova lista para ser substituída pela lista do input
		post.setTags(new ArrayList<>());
		modelMapper.map(postInput, post);
	}
	
}
