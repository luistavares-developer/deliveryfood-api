package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.RestauranteApenasNomeModelAssembler;
import com.deliveryfood.api.assembler.RestauranteAssembler;
import com.deliveryfood.api.assembler.RestauranteBasicoModelAssembler;
import com.deliveryfood.api.model.RestauranteApenasNomeModel;
import com.deliveryfood.api.model.RestauranteBasicoModel;
import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.api.openapi.controller.RestauranteControllerOpenApi;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteAssembler restauranteAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoAssembler;

	@GetMapping
	public CollectionModel<RestauranteBasicoModel> findAll() {

		return restauranteBasicoAssembler.toCollectionModel(restauranteService.findAll());
	}

	@GetMapping(params = "projecao=id-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarIdNome() {
		return restauranteApenasNomeAssembler.toCollectionModel(restauranteService.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel findById(@PathVariable Long restauranteId) {

		return restauranteAssembler.toModel(restauranteService.findById(restauranteId));
	}

	@PostMapping
	public ResponseEntity<RestauranteModel> save(@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restaurante = restauranteAssembler.toDomain(restauranteInput);
		RestauranteModel novoRestaurante = restauranteAssembler.toModel(restauranteService.save(restaurante));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<RestauranteModel> update(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = restauranteService.findById(restauranteId);
		restauranteAssembler.copyPropetiesToDomain(restauranteInput, restauranteAtual);
		RestauranteModel restauranteAtualizado = restauranteAssembler.toModel(restauranteService.save(restauranteAtual));

		return ResponseEntity.ok(restauranteAtualizado);
	}

	@PutMapping("/{restauranteId}/ativo")
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	public ResponseEntity<Void> ativarLote(@RequestBody List<Long> restauranteIds) {
		restauranteService.ativarLote(restauranteIds);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/inativo")
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/inativacoes")
	public ResponseEntity<Void> inativarLote(@RequestBody List<Long> restauranteIds) {
		restauranteService.inativarLote(restauranteIds);
	
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{restauranteId}/abertura")
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/fechamento")
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
}
