package com.algaworks.alganews.users.api.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SkillModel {
	
	@NotBlank
	@Size(max = 50)
	private String name;
	
	@NotNull
	@Min(0)
	@Max(100)
	private Integer percentage;
	
}
