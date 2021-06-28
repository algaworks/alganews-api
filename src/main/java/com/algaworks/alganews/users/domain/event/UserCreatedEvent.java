package com.algaworks.alganews.users.domain.event;

import com.algaworks.alganews.users.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreatedEvent {

	private final User user;

}
