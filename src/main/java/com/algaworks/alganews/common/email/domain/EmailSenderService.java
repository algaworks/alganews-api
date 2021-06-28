package com.algaworks.alganews.common.email.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EmailSenderService {

	void send(Message message);
	
	@Getter
	@Builder
	class Message {
		
		@Singular("to")
		private Set<String> recipient;
		
		@NonNull
		private String subject;
		
		@NonNull
		private String body;
		
		@Singular("data")
		private Map<String, Object> data;
		
	}
	
}
