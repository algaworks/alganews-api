package com.algaworks.alganews.files.validation;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AllowedFileExtensionsValidator
		implements ConstraintValidator<AllowedFileExtensions, String> {
	
	private final StorageProperties storageProperties;
	
	public final boolean isValid(final String fileName,
								 final ConstraintValidatorContext context) {
		
		if (StringUtils.isBlank(fileName)) {
			return true;
		}
		
		if (storageProperties.getAllowedExtensions().isEmpty()) {
			return true;
		}
		
		for (String extension : storageProperties.getAllowedExtensions()) {
			if (fileName.endsWith(extension)) {
				return true;
			}
		}
		
		((ConstraintValidatorContextImpl) context).addMessageParameter("extensions", getFormattedExtensions());
		
		return false;
	}
	
	private String getFormattedExtensions() {
		String extensions = "";
		List<String> allowedExtensions = new ArrayList<>(storageProperties.getAllowedExtensions());
		
		for (int i = 0; i < allowedExtensions.size(); i++) {
			extensions += allowedExtensions.get(i);
			if (i == allowedExtensions.size()-2) {
				extensions += " ou ";
			} else if (i != allowedExtensions.size()-1) {
				extensions += ", ";
			}
		}
		
		return extensions;
	}
}

