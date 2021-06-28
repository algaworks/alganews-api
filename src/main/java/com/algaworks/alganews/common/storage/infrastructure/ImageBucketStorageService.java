package com.algaworks.alganews.common.storage.infrastructure;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.ImageStorageService;
import com.algaworks.alganews.common.storage.domain.StorageException;
import com.algaworks.alganews.common.storage.domain.StorageFileAlreadyExistsException;
import com.algaworks.alganews.common.storage.domain.StorageFileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.algaworks.alganews.common.util.ImageContentTypeExtractor.getContentTypeByExtension;

@Service
@AllArgsConstructor
@Slf4j
public class ImageBucketStorageService implements ImageStorageService {

	protected final StorageProperties storageProperties;
	protected final StorageProvider storageProvider;
	
	@Override
	public void makePermanent(Path filePath) {
		BucketFileReference tmpFile = getTmpFileReference(filePath);
		BucketFileReference permanentFile = getPermanentFileReference(filePath);
		moveFile(tmpFile, permanentFile);
	}
	
	@Override
	public void removeFile(Path filePath) {
		final String bucketName = identifyBucketByFilePath(filePath);
		BucketFileReference file = new BucketFileReference(filePath, bucketName);
		removeFile(file);
	}
	
	@Override
	public boolean fileExists(Path filePath) {
		if (filePath == null) return false;
		final String bucketName = identifyBucketByFilePath(filePath);
		BucketFileReference file = new BucketFileReference(filePath, bucketName);
		try {
			return storageProvider.fileExists(file);
		} catch (Exception e) {
			throw new StorageException(String.format("Não foi possível excluir o arquivo %s.", filePath), e);
		}
	}
	
	@Override
	public URL generatePreSignedUploadUrl(String fileName, Long contentLength) {
		SignRequest signRequest = createSignRequest(fileName, contentLength);
		try {
			return storageProvider.createSignedUrl(signRequest);
		} catch (Exception e) {
			throw new StorageException(
					String.format("Não foi possível gerar a uri pré-assinada para o arquivo %s.", fileName), e);
		}
	}
	
	@Override
	public void verifyIfImageExists(Path filePath) {
		if (fileNotExist(getTempPath(filePath)))
			throw new StorageFileNotFoundException(filePath);
	}
	
	@Override
	public URI getDownloadUri() {
		return storageProvider.getDownloadUri();
	}
	
	@Override
	public URI getUploadUri() {
		return storageProvider.getUploadUri();
	}
	
	private Path getTempPath(Path filePath) {
		if (filePath == null) return null;
		String fileName = filePath.getFileName().toString();
		return Paths.get(String.format("%s/%s", storageProperties.getTempFolder(), fileName));
	}
	
	private BucketFileReference getTmpFileReference(Path filePath) {
		Path tempFilePath = getTempPath(filePath);
		String tmpBucket = storageProperties.getTempBucket();
		return new BucketFileReference(tempFilePath, tmpBucket);
	}
	
	private BucketFileReference getPermanentFileReference(Path filePath) {
		String bucket = storageProperties.getPhotosBucket();
		return new BucketFileReference(filePath, bucket);
	}
	
	private void moveFile(BucketFileReference from, BucketFileReference to) {
		copyFile(from, to);
		removeFile(from);
	}
	
	private void verifyIfImageExists(BucketFileReference fileReference) {
		if (storageProvider.fileExists(fileReference))
			throw new StorageFileAlreadyExistsException(fileReference.getPath());
	}
	
	private void verifyIfDoesNotExist(BucketFileReference fileReference) {
		if (!storageProvider.fileExists(fileReference))
			throw new StorageFileNotFoundException(fileReference.getPath());
	}
	
	private void copyFile(BucketFileReference from, BucketFileReference to) {
		verifyIfDoesNotExist(from);
		verifyIfImageExists(to);
		storageProvider.copyFile(from, to);
	}
	
	private void removeFile(BucketFileReference file) {
		try {
			storageProvider.removeFile(file);
		} catch (Exception e) {
			throw new StorageException(String.format("Não foi possível excluir o arquivo %s.", file.getPath()), e);
		}
	}
	
	private String identifyBucketByFilePath(Path filePath) {
		if (isTmpFile(filePath))
			return storageProperties.getTempBucket();
		return storageProperties.getPhotosBucket();
	}
	
	private SignRequest createSignRequest(String fileName, Long contentLength) {
		return SignRequest.builder()
				.filePath(Paths.get(storageProperties.getTempFolder(), fileName))
				.bucketName(storageProperties.getTempBucket())
				.contentType(getContentTypeByExtension(FilenameUtils.getExtension(fileName)))
				.contentLength(contentLength)
				.build();
	}
	
	private boolean isTmpFile(Path filePath) {
		return filePath.toString().startsWith(storageProperties.getTempFolder());
	}
	

}
