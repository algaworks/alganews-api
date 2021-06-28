package com.algaworks.alganews.common.storage.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

import static com.algaworks.alganews.common.util.URIExtractor.extractFileName;

@Getter
@AllArgsConstructor
@Builder
public class BucketFileReference {
	
	private final Path path;
	private final String bucketName;
	
	public String getFileName() {
		return extractFileName(path);
	}
	
}
