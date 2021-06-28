package com.algaworks.alganews.api.users.controller;

import com.algaworks.alganews.AbstractIT;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import com.algaworks.alganews.users.domain.model.Role;
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
		"alganews.security.default-user-id-if-disabled=2"
})
class UserControllerAssistantIT extends AbstractIT {
	
	@BeforeEach
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldReturnStatus200_WhenAssistantIsGettingAnManager() {
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
	public void shouldReturnStatus200_WhenAssistantIsGettingAnAssistant() {
		UserDetailedModel model = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", assistantId)
				.when()
				.get("/{id}")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(UserDetailedModel.class);
		
		Assertions.assertEquals(model.getId(), assistantId);
	}
	
	@Test
	public void shouldReturnStatus200_WhenAssistantIsGettingAnEditor() {
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
	
	@Test
	public void shouldReturnStatus201_WhenAssistantIsCreatingAnAssistant() {
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
		
		Assertions.assertEquals(model.getCreatedBy().getId(), assistantId);
		Assertions.assertEquals(model.getUpdatedBy().getId(), assistantId);
		
		Assertions.assertNotNull(model.getCreatedAt());
		Assertions.assertNotNull(model.getUpdatedBy());
	}
	
	@Test
	public void shouldReturnStatus201_WhenAssistantIsCreatingAnEditor() {
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
		
		Assertions.assertEquals(model.getCreatedBy().getId(), assistantId);
		Assertions.assertEquals(model.getUpdatedBy().getId(), assistantId);
		
		Assertions.assertNotNull(model.getCreatedAt());
		Assertions.assertNotNull(model.getUpdatedBy());
	}
	
	@Test
	public void shouldReturnStatus400_WhenEditorIsEditingTheEmailOfAManager() {
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
		
		model.setEmail("mail@mail.com");
		
		given()
				.body(model)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", managerId)
			.when()
				.put("/{id}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
		
	}
	
	@Test
	public void shouldReturnStatus400_WhenEditorIsEditingThePhoneOfAManager() {
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
		
		model.setPhone("123");
		
		given()
				.body(model)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", managerId)
			.when()
				.put("/{id}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
		
	}
	
	@Test
	public void shouldReturnStatus400_WhenEditorIsEditingTheRoleOfAManager() {
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
		
		model.setRole(Role.EDITOR);
		
		given()
				.body(model)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", managerId)
			.when()
				.put("/{id}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
		
		
	}
	
	@Test
	public void shouldReturnStatus400_WhenAssistantIsCreatingAManager() {
		given()
				.body(validManageJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

}