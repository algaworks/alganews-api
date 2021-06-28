package com.algaworks.alganews.common.storage.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
@Builder
public class SignRequest {
	
	private final String bucketName;
	private final Path filePath;
	private final String contentType;
	private final Long contentLength;
	
}
