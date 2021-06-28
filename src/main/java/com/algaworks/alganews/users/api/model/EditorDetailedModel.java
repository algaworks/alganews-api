package com.algaworks.alganews.users.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.alganews.common.api.model.LocationModel;

@Setter
@Getter
public class EditorDetailedModel {
	
	private Long id;
	private String name;
	private ImageUrlsModel avatarUrls;
	private OffsetDateTime createdAt;
	private String bio;
	private LocationModel location;
	private List<SkillModel> skills = new ArrayList<>();
	
}
