package com.algaworks.alganews.common.storage.infrastructure;

import java.net.URI;
import java.net.URL;

public interface StorageProvider {
	
	void copyFile(BucketFileReference fromFile, BucketFileReference toFile);
	void removeFile(BucketFileReference permanentFile);
	
	boolean fileExists(BucketFileReference fileReference);
	
	URL createSignedUrl(SignRequest signRequest);
	
	URI getDownloadUri();
	URI getUploadUri();
	
}
