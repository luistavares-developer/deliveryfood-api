package com.deliveryfood.api.model;

import com.deliveryfood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@ApiModelProperty(example = "Indiana")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}