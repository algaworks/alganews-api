package com.algaworks.alganews.common.storage.gcp.infrastructure;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.infrastructure.BucketFileReference;
import com.algaworks.alganews.common.storage.infrastructure.SignRequest;
import com.algaworks.alganews.common.storage.infrastructure.StorageProvider;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "alganews.storage.type", havingValue = "gcp")
@Slf4j
@AllArgsConstructor
public class GoogleCloudStorageProvider implements StorageProvider {
	
	private final Storage storage = StorageOptions.getDefaultInstance().getService();
	private final StorageProperties storageProperties;
	
	@Override
	public void copyFile(BucketFileReference fromFile, BucketFileReference toFile) {
		Blob blob = getFile(fromFile);
		blob.copyTo(toFile.getBucketName(), toFile.getPath().toString());
		blob.delete();
	}
	
	@Override
	public boolean fileExists(BucketFileReference fileReference) {
		return getFile(fileReference) != null;
	}
	
	@Override
	public void removeFile(BucketFileReference fileReference) {
		Blob blob = getFile(fileReference);
		if (blob != null) {
			blob.delete();
		}
	}
	
	@Override
	public URL createSignedUrl(SignRequest signRequest) {
		BlobInfo blobInfo = BlobInfo.newBuilder(getBlobId(signRequest)).build();
		
		Map<String, String> extensionHeaders = new HashMap<>();
		extensionHeaders.put("Content-Type", signRequest.getContentType());
		
		if (signRequest.getContentLength() != null)
			extensionHeaders.put("Content-Length", signRequest.getContentLength().toString());
		
		String uploadHost = storageProperties.getGcp().getUploadUrl().getHost();
		
		return storage.signUrl(
				blobInfo,
				15,
				TimeUnit.MINUTES,
				SignUrlOption.httpMethod(com.google.cloud.storage.HttpMethod.PUT),
				SignUrlOption.withExtHeaders(extensionHeaders),
				SignUrlOption.withBucketBoundHostname(uploadHost, Storage.UriScheme.HTTPS),
				SignUrlOption.withV4Signature()
		);
	}
	
	@Override
	public URI getDownloadUri() {
		return storageProperties.getGcp().getDownloadUrl();
	}
	
	@Override
	public URI getUploadUri() {
		return storageProperties.getGcp().getUploadUrl();
	}
	
	private BlobId getBlobId(SignRequest signRequest) {
		return BlobId.of(signRequest.getBucketName(), signRequest.getFilePath().toString());
	}
	
	private Blob getFile(BucketFileReference fromFile) {
		Bucket bucket = getBucket(fromFile.getBucketName());
		return bucket.get(fromFile.getPath().toString());
	}
	
	private Bucket getBucket(String bucketName) {
		return storage.get(bucketName);
	}
}
