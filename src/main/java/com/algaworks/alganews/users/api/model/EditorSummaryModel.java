package com.algaworks.alganews.users.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class EditorSummaryModel {
	
	private Long id;
	private String name;
	private ImageUrlsModel avatarUrls;
	private OffsetDateTime createdAt;
	
}
