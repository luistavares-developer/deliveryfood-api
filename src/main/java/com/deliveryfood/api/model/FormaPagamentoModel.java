package com.deliveryfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoModel {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Crédito")
	private String descricao;
}