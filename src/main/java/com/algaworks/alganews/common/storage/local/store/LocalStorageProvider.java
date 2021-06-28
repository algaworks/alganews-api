package com.algaworks.alganews.common.storage.local.store;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.StorageException;
import com.algaworks.alganews.common.storage.domain.StorageFileAlreadyExistsException;
import com.algaworks.alganews.common.storage.domain.StorageFileEmptyException;
import com.algaworks.alganews.common.storage.infrastructure.BucketFileReference;
import com.algaworks.alganews.common.storage.infrastructure.SignRequest;
import com.algaworks.alganews.common.storage.infrastructure.StorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "alganews.storage.type", havingValue = "local")
public class LocalStorageProvider implements StorageProvider {
	
	private final StorageProperties storageProperties;
	private final ApplicationEventPublisher eventPublisher;
	
	@Override
	public void copyFile(BucketFileReference fromFile, BucketFileReference toFile) {
		Path tempFilePath = getLocalStorageFullPath(fromFile.getPath());
		Path localStoragePath = getLocalStorageFullPath(toFile.getPath());
		
		copy(tempFilePath, localStoragePath);
		
		removeFileByPath(tempFilePath);
		eventPublisher.publishEvent(new LocalStorageFileCreatedEvent(localStoragePath, this.getClass()));
	}
	
	@Override
	public boolean fileExists(BucketFileReference fileReference) {
		return fileExists(fileReference.getPath());
	}
	
	@Override
	public void removeFile(BucketFileReference permanentFile) {
		removeFileByPath(getLocalStorageFullPath(permanentFile.getPath()));
	}
	
	@Override
	public URL createSignedUrl(SignRequest signRequest) {
		try {
			return new URL(UriComponentsBuilder.fromUri(storageProperties.getLocal().getUploadUrl())
					.pathSegment(signRequest.getFilePath().getParent().toString())
					.pathSegment(signRequest.getFilePath().getFileName().toString())
					.toUriString());
		} catch (MalformedURLException e) {
			throw new StorageException("Não foi possível gerar a uri pré-assinada.", e);
		}
	}
	
	@Override
	public URI getDownloadUri() {
		return storageProperties.getLocal().getDownloadUrl();
	}
	
	@Override
	public URI getUploadUri() {
		return storageProperties.getLocal().getUploadUrl();
	}
	
	public void store(InputStream inputStream, Path filePath) {
		if (fileExists(filePath))
			throw new StorageFileAlreadyExistsException(filePath);
		
		Path localStoragePath = getLocalStorageFullPath(filePath);
		
		try {
			copyStreamToPath(inputStream, localStoragePath);
		} catch (StorageFileEmptyException e) {
			throw e;
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}
	
	public File recover(Path filePath) {
		try {
			Path localStoragePath = getLocalStorageFullPath(filePath);
			return new File(localStoragePath.toString());
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo.", e);
		}
	}
	
	private void copyStreamToPath(InputStream inputStream, Path localStoragePath) throws IOException {
		createParentDirectories(localStoragePath);
		
		OutputStream outputStream = Files.newOutputStream(localStoragePath);
		FileCopyUtils.copy(inputStream, outputStream);
		
		verifyIfFileIsEmpty(localStoragePath);
	}
	
	private void verifyIfFileIsEmpty(Path localStoragePath) throws IOException {
		File file = new File(localStoragePath.toUri());
		if (file.length() == 0) {
			Files.delete(localStoragePath);
			throw new StorageFileEmptyException();
		}
	}
	
	private void removeFileByPath(Path filePath) {
		if (filePath == null) return;
		deleteIfExists(filePath);
		eventPublisher.publishEvent(new LocalStorageFileRemovedEvent(filePath, this.getClass()));
	}
	
	private void deleteIfExists(Path storagePath) {
		try {
			Files.deleteIfExists(storagePath);
		} catch (Exception e) {
			throw new StorageException(String.format("Não foi possível excluir o arquivo %s.", storagePath), e);
		}
	}
	
	private boolean fileExists(Path path) {
		Path localStoragePath = getLocalStorageFullPath(path);
		return Files.exists(localStoragePath);
	}
	
	private void copy(Path tempFilePath, Path localStoragePath) {
		try {
			createParentDirectories(localStoragePath);
			Files.copy(tempFilePath, localStoragePath);
		} catch (IOException e) {
			throw new StorageException("Não foi mover o arquivo para pasta permanente.", e);
		}
	}
	
	private Path getLocalStorageFullPath(Path filePath) {
		return Paths.get(storageProperties.getLocal().getStoragePath()).resolve(filePath) ;
	}
	
	private void createParentDirectories(Path localStoragePath){
		try {
			Files.createDirectories(localStoragePath.getParent());
		} catch (IOException e) {
			throw new StorageException(String.format("Não foi armazenar criar a pasta %s.", localStoragePath.getParent()), e);
		}
	}
	

}
