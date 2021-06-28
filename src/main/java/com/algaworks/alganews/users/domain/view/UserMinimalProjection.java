package com.algaworks.alganews.users.domain.view;

import com.algaworks.alganews.users.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMinimalProjection {
	
	private Long id;
	private String name;
	private String avatar;
	
	public UserMinimalProjection(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.avatar = user.getAvatar();
	}
}
