package com.algaworks.alganews.users.api.model;

import java.time.OffsetDateTime;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import com.algaworks.alganews.users.domain.model.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSummaryModel {

	private Long id;
	private String name;
	private String email;
	private ImageUrlsModel avatarUrls;
	private Role role;
	private boolean active;
	private OffsetDateTime createdAt;
	private boolean canBeActivated;
	private boolean canBeDeactivated;
	private boolean canSensitiveDataBeUpdated;
	
}
