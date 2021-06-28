package com.algaworks.alganews.common.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrlsModel {
	
	@JsonProperty("default")
	private String defaultUrl;
	private String small;
	private String medium;
	private String large;
	
}
