package com.algaworks.alganews.api.users.controller;

import com.algaworks.alganews.AbstractIT;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties", properties = {
		"alganews.security.default-user-id-if-disabled=3"
})
class UserControllerEditorIT extends AbstractIT {
	
	@BeforeEach
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldReturnStatus200_WhenEditorIsGettingAnItself() {
		UserDetailedModel model = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", editorId)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
			.extract()
				.as(UserDetailedModel.class);
		
		Assertions.assertEquals(model.getId(), editorId);
	}
	
	//falha por conta que não tem security ativado
	@Test
	public void shouldReturnStatus403_WhenEditorIsGettingAnUser() {
		given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", managerId)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.FORBIDDEN.value());
	}
	
	//falha por conta que não tem security ativado
	@Test
	public void shouldReturnStatus403_WhenManagerIsListingUsers() {
		given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.FORBIDDEN.value());
	}

}