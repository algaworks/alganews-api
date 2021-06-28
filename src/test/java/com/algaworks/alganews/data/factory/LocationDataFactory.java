package com.algaworks.alganews.data.factory;

import com.algaworks.alganews.users.domain.model.Location;

public class LocationDataFactory {
	public static Location createDefault() {
		return Location.builder()
				.country("Brasil")
				.state("Minas Gerais")
				.city("Uberlândia")
				.build();
	}
}