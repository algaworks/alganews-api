package com.algaworks.alganews.common.storage.local.controller;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.StorageFileEmptyException;
import com.algaworks.alganews.common.storage.local.store.LocalStorageProvider;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static com.algaworks.alganews.common.util.ImageContentTypeExtractor.getContentTypeByExtension;

@RestController
@ConditionalOnProperty(name = "alganews.storage.type", havingValue = "local")
@AllArgsConstructor
@Slf4j
public class LocalStorageController {
	
	private final LocalStorageProvider localStorageService;
	private final StorageProperties storageProperties;
	
	@PutMapping(value = "/uploads/{folder}/{fileName}")
	public void upload(@PathVariable("folder") String folder,
					   @PathVariable("fileName") String fileName,
					   InputStream inputStream,
					   HttpServletRequest request) throws HttpMediaTypeNotSupportedException {
		
		String contentType = request.getContentType();
		
		if (!isValid(contentType)) {
			throw new HttpMediaTypeNotSupportedException(contentType);
		}
		
		try {
			localStorageService.store(inputStream, Paths.get(folder, fileName));
		} catch (StorageFileEmptyException e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	private boolean isValid(String contentType) {
		for (String allowedExtension : storageProperties.getAllowedExtensions())
			if (getContentTypeByExtension(allowedExtension).equals(contentType))
				return true;
		return false;
	}
	
	@SneakyThrows
	@GetMapping(value = {"/uploads/{folder}/{fileName}", "/downloads/{folder}/{fileName}", "/downloads/{folder}/{imgVersion}/{fileName}"})
	public ResponseEntity<Resource> download(@PathVariable("folder") String folder,
											 @PathVariable("fileName") String fileName,
											 @PathVariable(value = "imgVersion", required = false) String imgVersion,
											 InputStream inputStream, HttpServletRequest request) throws FileNotFoundException {
		Path imgPath = createImagePath(folder, fileName, imgVersion);
		File file = localStorageService.recover(imgPath);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		return ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(getMediaType(file))
				.body(resource);
	}
	
	private Path createImagePath(String folder,
	                             String fileName,
	                             String imgVersion) {
		if (imgVersion == null) {
			return Paths.get(folder, fileName);
		} else {
			return Paths.get(folder, imgVersion, fileName);
		}
	}
	
	private MediaType getMediaType(File file) {
		String extension = FilenameUtils.getExtension(file.getName());
		
		if (extension.equalsIgnoreCase("jpg")) {
			extension = "jpeg";
		}
		
		return MediaType.valueOf("image/" + extension.toLowerCase());
	}
	
}
