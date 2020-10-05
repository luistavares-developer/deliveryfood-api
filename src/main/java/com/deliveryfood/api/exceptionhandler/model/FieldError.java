package com.deliveryfood.api.exceptionhandler.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel("CamposComErro")
@Getter
@AllArgsConstructor
public class FieldError {

	private String name;
	private String description;	
}
