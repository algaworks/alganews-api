package com.algaworks.alganews.files.validation;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class AllowedContentLengthValidator
		implements ConstraintValidator<AllowedContentLength, Long> {
	
	private final StorageProperties storageProperties;
	
	public final boolean isValid(final Long fileLength,
								 final ConstraintValidatorContext context) {
		
		Long fileLengthLimit = storageProperties.getFileLengthLimit();
		
		((ConstraintValidatorContextImpl) context)
				.addMessageParameter("fileLengthLimitFormatted",
						FileUtils.byteCountToDisplaySize(fileLengthLimit));
		
		if (fileLength == null) {
			return true;
		}
		
		return fileLength < fileLengthLimit && fileLength > 0;
	}
}

