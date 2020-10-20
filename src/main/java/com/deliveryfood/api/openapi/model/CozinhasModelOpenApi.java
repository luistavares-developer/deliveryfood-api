package com.deliveryfood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Link;

import com.deliveryfood.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	
	private CozinhaEmbeddedModelOpenApi _embedded;
	private Link _links;
	private PageModelOpenApi page;

	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhaEmbeddedModelOpenApi {
		private List<CozinhaModel> cozinhas;
	}
}