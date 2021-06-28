package com.algaworks.alganews.data.factory;

import com.algaworks.alganews.users.domain.model.Role;
import com.algaworks.alganews.users.domain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class UserDataFactory {
	
	private UserDataFactory() { }

	public static User createManager() {
		User manager = buildDefaultUser()
				.role(Role.MANAGER)
				.name("Alganews Manager")
				.email("manager@alganews.com.br")
				.build();
		manager.setSkills(SkillDataFactory.createDefaultList(manager));
		return manager;
	}
	
	public static User createAssistant() {
		User assistant = buildDefaultUser()
				.role(Role.ASSISTANT)
				.name("Alganews assistant")
				.email("assistant@alganews.com.br")
				.build();
		assistant.setSkills(SkillDataFactory.createDefaultList(assistant));
		return assistant;
	}
	
	public static User createEditor() {
		User editor = buildDefaultUser()
				.role(Role.EDITOR)
				.name("Alganews editor")
				.email("editor@alganews.com.br")
				.build();
		editor.setSkills(SkillDataFactory.createDefaultList(editor));
		return editor;
	}
	
	public static User.UserBuilder buildDefaultUser() {
		return User.builder()
				.password(new BCryptPasswordEncoder().encode("alganews"))
				.birthdate(LocalDate.of(1999, 1, 1))
				.pricePerWord(BigDecimal.ONE)
				.phone("(15) 91234-1234")
				.taxpayerId("191.000.000-00")
				.bio("Small bio")
				.location(LocationDataFactory.createDefault())
				.bankAccount(BankAccountDataFactory.createDefault())
				.skills(SkillDataFactory.createDefaultList())
				.lastLogin(OffsetDateTime.now())
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.active(true);
	}
	
}
