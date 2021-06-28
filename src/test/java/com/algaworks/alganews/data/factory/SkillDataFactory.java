package com.algaworks.alganews.data.factory;

import com.algaworks.alganews.users.domain.model.Skill;
import com.algaworks.alganews.users.domain.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillDataFactory {
	
	private SkillDataFactory() {}
	
	public static List<Skill> createDefaultList() {
		return new ArrayList<>(
				Arrays.asList(
						Skill.builder().name("Javascript").percentage(50).build(),
						Skill.builder().name("Java").percentage(30).build(),
						Skill.builder().name("Typescript").percentage(20).build()
				)
		);
	}
	
	public static List<Skill> createDefaultList(User user) {
		return new ArrayList<>(
				Arrays.asList(
						Skill.builder().name("Javascript").user(user).percentage(50).build(),
						Skill.builder().name("Java").user(user).percentage(30).build(),
						Skill.builder().name("Typescript").user(user).percentage(20).build()
				)
		);
	}
}
