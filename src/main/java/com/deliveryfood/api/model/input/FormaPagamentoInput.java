package com.deliveryfood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoInput {

	@ApiModelProperty(example = "Crédito", required = true)
	@NotBlank
	private String descricao;
}
