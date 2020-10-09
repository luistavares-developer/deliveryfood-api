package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.ProdutoAssembler;
import com.deliveryfood.api.model.ProdutoModel;
import com.deliveryfood.api.model.input.ProdutoInput;
import com.deliveryfood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.ProdutoService;
import com.deliveryfood.domain.service.RestauranteService;


@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoAssembler produtoConverter;

	@GetMapping
	public List<ProdutoModel> findAll(@PathVariable Long restauranteId, 
			@RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		
		List<Produto> produtos = null;
		
		if(incluirInativos) {
			produtos = produtoService.findByRestaurante(restaurante);
		} else {
			produtos = produtoService.findAtivosByRestaurante(restaurante);
		}

		return produtoConverter.toCollectionModel(produtos);
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		Produto Produto = produtoService.findById(restauranteId, produtoId);
		return produtoConverter.toModel(Produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel save(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		Produto produto = produtoConverter.toDomain(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoConverter.toModel(produtoService.save(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoModel update(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		
		Produto produtoAtual = produtoService.findById(restauranteId, produtoId);
		
		produtoConverter.copyPropetiesToDomain(produtoInput, produtoAtual);
		
		return produtoConverter.toModel(produtoService.save(produtoAtual));
	}

}
