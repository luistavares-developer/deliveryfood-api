package com.deliveryfood.api.exceptionhandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldError {

	private String name;
	private String description;	
}
