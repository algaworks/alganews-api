package com.algaworks.alganews.common.storage.local.store;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;

@Getter
public class LocalStorageFileCreatedEvent extends ApplicationEvent {
	
	private final Path filePath;
	
	public LocalStorageFileCreatedEvent(Path filePath, Class source) {
		super(source);
		this.filePath = filePath;
	}
}
