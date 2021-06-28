package com.algaworks.alganews.posts.api.model;

import java.time.OffsetDateTime;
import java.util.Set;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import com.algaworks.alganews.users.api.model.EditorSummaryModel;
import com.algaworks.alganews.users.api.model.UserMinimalModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailedModel {
	
	private Long id;
	private String title;
	private String slug;
	private String body;
	private Set<String> tags;
	private boolean published;
	
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	
	private boolean canBePublished;
	private boolean canBeUnpublished;
	private boolean canBeDeleted;
	private boolean canBeEdited;
	
	@JsonInclude(Include.NON_NULL)
	private PostEarningsModel earnings;
	private EditorSummaryModel editor;
	private UserMinimalModel updatedBy;
	private ImageUrlsModel imageUrls;
	
}
