package com.algaworks.alganews.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
	
	@Column(name = "location_country")
	private String country;
	
	@Column(name = "location_state")
	private String state;
	
	@Column(name = "location_city")
	private String city;
	
}
