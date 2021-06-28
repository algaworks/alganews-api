package com.algaworks.alganews.common.storage.domain;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public interface ImageStorageService {
	
	void makePermanent(Path filePath);
	void removeFile(Path filePath);
	void verifyIfImageExists(Path filePath);
	
	boolean fileExists(Path filePath);
	default boolean fileNotExist(Path filePath) {
		return !fileExists(filePath);
	}
	
	URL generatePreSignedUploadUrl(String fileName, Long contentLength);
	
	URI getDownloadUri();
	URI getUploadUri();
	
}


