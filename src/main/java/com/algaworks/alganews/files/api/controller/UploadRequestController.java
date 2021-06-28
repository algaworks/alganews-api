package com.algaworks.alganews.files.api.controller;

import java.net.URL;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.common.storage.domain.ImageStorageService;
import com.algaworks.alganews.files.api.model.UploadRequestInput;
import com.algaworks.alganews.files.api.model.UploadRequestModel;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/upload-requests")
@AllArgsConstructor
public class UploadRequestController {
	
	private final ImageStorageService imageStorageService;

	@CheckSecurity.UploadRequest.CanCreate
	@PostMapping
	public UploadRequestModel create(@RequestBody @Valid UploadRequestInput uploadRequest) {
		URL url = imageStorageService.generatePreSignedUploadUrl(uploadRequest.getFileName(), uploadRequest.getContentLength());
		return new UploadRequestModel(url.toString());
	}

}
