package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.RestauranteConverter;
import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.api.model.view.RestauranteView;
import com.deliveryfood.api.openapi.controller.RestauranteControllerOpenApi;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteConverter restauranteConverter;

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> findAll() {

		return restauranteConverter.toCollectionModel(restauranteService.findAll());
	}

	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=id-nome")
	public List<RestauranteModel> listarIdNome() {
		return findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel findById(@PathVariable Long restauranteId) {

		return restauranteConverter.toModel(restauranteService.findById(restauranteId));
	}

	@PostMapping
	public ResponseEntity<RestauranteModel> save(@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restaurante = restauranteConverter.toDomain(restauranteInput);
		RestauranteModel novoRestaurante = restauranteConverter.toModel(restauranteService.save(restaurante));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<RestauranteModel> update(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = restauranteService.findById(restauranteId);
		restauranteConverter.copyPropetiesToDomain(restauranteInput, restauranteAtual);
		RestauranteModel restauranteAtualizado = restauranteConverter.toModel(restauranteService.save(restauranteAtual));

		return ResponseEntity.ok(restauranteAtualizado);
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarLote(@RequestBody List<Long> restauranteIds) {
		restauranteService.ativarLote(restauranteIds);
	}

	@PutMapping("/{restauranteId}/inativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
	}
	
	@PutMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarLote(@RequestBody List<Long> restauranteIds) {
		restauranteService.inativarLote(restauranteIds);
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechamento(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
	}
}
