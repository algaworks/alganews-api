package com.algaworks.alganews.files.api.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.alganews.files.validation.AllowedContentLength;
import com.algaworks.alganews.files.validation.AllowedFileExtensions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadRequestInput {
	
	@NotBlank
	@AllowedFileExtensions
	private String fileName;
	
	@NotNull
	@AllowedContentLength
	private Long contentLength;
	
}
