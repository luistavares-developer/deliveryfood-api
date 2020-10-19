package com.deliveryfood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Link;

import com.deliveryfood.api.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

	private CidadeEmbeddedModelOpenApi _embedded;
	private Link _links;

	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi {
		private List<CidadeModel> cidades;
	}
}
