package com.algaworks.alganews.common.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LocationModel {
	
	@NotBlank
	@Size(max = 50)
	private String country;
	
	@NotBlank
	@Size(max = 50)
	private String state;
	
	@NotBlank
	@Size(max = 255)
	private String city;

}
