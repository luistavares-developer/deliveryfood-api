package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToDesvincularFormaPagamentoDeRestaurante;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestauranteFormasPagamento;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToVincularFormaPagamentoDeRestaurante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.FormaPagamentoAssembler;
import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormasPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private FormaPagamentoAssembler formaPagamentoAssembler;

	@GetMapping
	public CollectionModel<FormaPagamentoModel> findAll(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteService.findById(restauranteId);
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoAssembler.toCollectionModel(
				restaurante.getFormasPagamento())
				.removeLinks()
				.add(linkToRestauranteFormasPagamento(restauranteId))
				.add(linkToVincularFormaPagamentoDeRestaurante(restauranteId, "vincular"));

		formasPagamentoModel.getContent().forEach(formaPagamento -> {
			formaPagamento.add(linkToDesvincularFormaPagamentoDeRestaurante(restaurante.getId(), formaPagamento.getId(), "desvincular"));
		});
		
		return formasPagamentoModel;
	}

	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> vincularFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desvincularFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}

}
