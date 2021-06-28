package com.algaworks.alganews.common.storage.domain;

import java.nio.file.Path;

public class StorageFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public StorageFileNotFoundException(Path path) {
		super(getMessage(path));
	}
	
	private static String getMessage(Path path) {
		return String.format("O arquivo %s não foi encontrado.", path.getFileName());
	}
	
}
