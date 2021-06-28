package com.algaworks.alganews.posts.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PostWithEarningsModel {
	
	private Long id;
	
	private String title;
	
	private OffsetDateTime createdAt;
	
	private ImageUrlsModel imageUrls;
	
	private boolean published;
	
	private PostEarningsModel earnings;

}
