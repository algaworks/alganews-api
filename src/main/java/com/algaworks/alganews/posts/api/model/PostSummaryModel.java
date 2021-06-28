package com.algaworks.alganews.posts.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

import com.algaworks.alganews.users.api.model.EditorSummaryModel;

@Getter
@Setter
public class PostSummaryModel {
	
	private Long id;
	private String title;
	private String slug;
	private ImageUrlsModel imageUrls;
	private boolean published;
	private Set<String> tags;
	
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	
	private boolean canBePublished;
	private boolean canBeUnpublished;
	private boolean canBeDeleted;
	private boolean canBeEdited;
	
	private EditorSummaryModel editor;

}
