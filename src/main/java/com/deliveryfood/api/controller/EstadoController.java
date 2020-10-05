package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.EstadoConverter;
import com.deliveryfood.api.model.EstadoModel;
import com.deliveryfood.api.model.input.EstadoInput;
import com.deliveryfood.api.openapi.controller.EstadoControllerOpenApi;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoConverter estadoConverter;

	@GetMapping
	public List<EstadoModel> findAll() {
		
		return estadoConverter.toCollectionModel(estadoService.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoModel findById(@PathVariable Long estadoId) {
		
		return estadoConverter.toModel(estadoService.findById(estadoId));
	}

	@PostMapping
	public ResponseEntity<EstadoModel> save(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoConverter.toDomain(estadoInput);
		EstadoModel novoEstado = estadoConverter.toModel(estadoService.save(estado));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<EstadoModel> update(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estadoAtual = estadoService.findById(estadoId);
		estadoConverter.copyPropetiesToDomain(estadoInput, estadoAtual);
		EstadoModel estadoAtualizado = estadoConverter.toModel(estadoService.save(estadoAtual));
		
		return ResponseEntity.ok(estadoAtualizado);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long estadoId) {
		estadoService.delete(estadoId);
	}
}
