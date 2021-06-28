package com.algaworks.alganews.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	
	MANAGER(2, "Gerente"),
	ASSISTANT(1, "Assistente"),
	EDITOR(0, "Editor");
	
	private final Integer level;
	private final String localizedName;
	
	public boolean isHigherOrEqual(Role role) {
		return this.getLevel() >= role.getLevel();
	}
	
	public boolean isLower(Role role) {
		return this.getLevel() < role.getLevel();
	}
	
}
