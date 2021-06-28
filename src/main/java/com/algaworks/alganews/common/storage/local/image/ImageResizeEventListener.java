package com.algaworks.alganews.common.storage.local.image;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.local.store.LocalStorageFileCreatedEvent;
import com.algaworks.alganews.common.storage.local.store.LocalStorageFileRemovedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@AllArgsConstructor
public class ImageResizeEventListener {
	
	private final ImageResizeService imageResizeService;
	private final StorageProperties storageProperties;
	
	@EventListener
	@Async
	public void onFileCreated(LocalStorageFileCreatedEvent event) {
		if (canIgnoreImage(event.getFilePath()))
			return;
		imageResizeService.createResizedVersions(event.getFilePath());
	}
	
	@EventListener
	@Async
	public void onFileDeleted(LocalStorageFileRemovedEvent event) {
		if (canIgnoreImage(event.getFilePath()))
			return;
		imageResizeService.deleteResizedVersions(event.getFilePath());
	}
	
	private boolean canIgnoreImage(Path path) {
		return path.toString().contains(storageProperties.getPostBodyImageFolder());
	}
	
}
