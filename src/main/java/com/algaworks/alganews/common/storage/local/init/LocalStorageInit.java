package com.algaworks.alganews.common.storage.local.init;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "alganews.storage.type", havingValue = "local")
public class LocalStorageInit implements ApplicationRunner {
	
	private final StorageProperties storageProperties;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Files.createDirectories(Paths.get(storageProperties.getLocal().getStoragePath()));
	}
}
