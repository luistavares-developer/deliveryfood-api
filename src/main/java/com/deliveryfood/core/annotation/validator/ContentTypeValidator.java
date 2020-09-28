package com.deliveryfood.core.annotation.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.core.annotation.ContentType;

public class ContentTypeValidator implements ConstraintValidator<ContentType, MultipartFile> {

	private List<String> contentTypePermitidos;
	
	@Override
	public void initialize(ContentType constraintAnnotation) {
		this.contentTypePermitidos = Arrays.asList(constraintAnnotation.allow());
	}
	
	@Override
	public boolean isValid(MultipartFile arquivo, ConstraintValidatorContext context) {
		return arquivo == null || contentTypePermitidos.contains(arquivo.getContentType());
	}

}