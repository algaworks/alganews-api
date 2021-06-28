package com.algaworks.alganews.common.storage.domain;

public class StorageFileEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public StorageFileEmptyException() {
		super("Arquivo enviado está vazio.");
	}
}
