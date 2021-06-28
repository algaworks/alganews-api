package com.algaworks.alganews.common.storage.domain;

import java.nio.file.Path;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}
	
	public StorageException(Path path) {
		super(getMessage(path));
	}
	
	public StorageException(Path path, Throwable cause) {
		super(getMessage(path));
	}
	
	private static String getMessage(Path path) {
		return String.format("Ocorreu um erro durante o armazenamento do arquivo %s.", path.getFileName());
	}

}
