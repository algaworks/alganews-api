package com.algaworks.alganews.posts.api.model;

import static com.algaworks.alganews.common.util.URIExtractor.extractFileName;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInput {
	
	@NotBlank
	@Size(max = 255)
	private String title;
	
	@NotBlank
	private String body;
	
	@NotBlank
	private String imageUrl;
	
	@NotEmpty
	private Set<String> tags;
	
	public String getImage() {
		return extractFileName(getImageUrl());
	}

}
