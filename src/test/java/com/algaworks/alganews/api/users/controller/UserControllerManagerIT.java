package com.algaworks.alganews.api.users.controller;

import com.algaworks.alganews.AbstractIT;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.domain.model.Role;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties", properties = {
		"alganews.security.default-user-id-if-disabled=1"
})
class UserControllerManagerIT extends AbstractIT {
	
	@BeforeEach
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldReturnStatus200_WhenManagerIsListingUsers() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldReturnStatus200_WhenManagerIsGettingAnUser() {
		UserDetailedModel model = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", managerId)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
			.extract()
				.as(UserDetailedModel.class);
		
		Assertions.assertEquals(model.getId(), managerId);
	}
	
	@Test
	public void shouldReturnStatus201_WhenManagerIsCreatingAnAssistant() {
		UserDetailedModel model = given()
				.body(validAssistantJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value())
			.extract()
				.as(UserDetailedModel.class);
		
		Assertions.assertNotNull(model.getId());
		Assertions.assertEquals(Role.ASSISTANT, model.getRole());
		
		Assertions.assertEquals(model.getCreatedBy().getId(), managerId);
		Assertions.assertEquals(model.getUpdatedBy().getId(), managerId);
		
		Assertions.assertNotNull(model.getCreatedAt());
		Assertions.assertNotNull(model.getUpdatedBy());
	}
	
	@Test
	public void shouldReturnStatus201_WhenManagerIsCreatingAnEditor() {
		UserDetailedModel model = given()
				.body(validEditorJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value())
			.extract()
				.as(UserDetailedModel.class);
		
		Assertions.assertNotNull(model.getId());
		Assertions.assertEquals(Role.EDITOR, model.getRole());
		
		Assertions.assertEquals(model.getCreatedBy().getId(), managerId);
		Assertions.assertEquals(model.getUpdatedBy().getId(), managerId);
		
		Assertions.assertNotNull(model.getCreatedAt());
		Assertions.assertNotNull(model.getUpdatedBy());
	}
	
	@Test
	public void shouldReturnStatus200_WhenManagerIsEditingAnUser() {
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
		
		ModelMapper modelMapper = new ModelMapper();
		UserInput userInput = new UserInput();
		modelMapper.map(model, userInput);
		
		given()
				.body(userInput)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", editorId)
				.when()
				.put("/{id}")
				.then()
				.statusCode(HttpStatus.OK.value());
		
		
	}

}