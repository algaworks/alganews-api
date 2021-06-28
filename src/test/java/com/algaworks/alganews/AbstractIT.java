package com.algaworks.alganews;

import com.algaworks.alganews.data.factory.UserDataFactory;
import com.algaworks.alganews.users.domain.repository.UserRepository;
import com.algaworks.alganews.util.DatabaseCleaner;
import com.algaworks.alganews.util.ResourceUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class AbstractIT {
	
	@LocalServerPort
	protected int port;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected DatabaseCleaner databaseCleaner;
	
	protected String validEditorJson;
	protected String validAssistantJson;
	protected String validManageJson;
	
	protected Long managerId;
	protected Long assistantId;
	protected Long editorId;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/users";
		
		validEditorJson = ResourceUtils.getContentFromResource("/json/valid/editor.json");
		validAssistantJson = ResourceUtils.getContentFromResource("/json/valid/assistant.json");
		validManageJson = ResourceUtils.getContentFromResource("/json/valid/manager.json");
		
		databaseCleaner.clearTables();
		setupData();
	}
	
	protected void setupData() {
		managerId = userRepository.save(UserDataFactory.createManager()).getId();
		assistantId = userRepository.save(UserDataFactory.createAssistant()).getId();
		editorId = userRepository.save(UserDataFactory.createEditor()).getId();
	}

}