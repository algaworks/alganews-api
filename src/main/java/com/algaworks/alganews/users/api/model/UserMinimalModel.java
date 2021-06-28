package com.algaworks.alganews.users.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserMinimalModel {

	private Long id;
	private String name;
	private ImageUrlsModel avatarUrls;
	
}
