package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.deliveryfood.api.converter.GrupoConverter;
import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoConverter grupoConverter;

	@GetMapping
	public List<GrupoModel> findAll() {
		
		return grupoConverter.toCollectionModel(grupoService.findAll());
	}

	@GetMapping("/{grupoId}")
	public GrupoModel findById(@PathVariable Long grupoId) {
		
		return grupoConverter.toModel(grupoService.findById(grupoId));
	}

	@PostMapping
	public ResponseEntity<GrupoModel> save(@RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupo = grupoConverter.toDomain(grupoInput);
		GrupoModel novoGrupo = grupoConverter.toModel(grupoService.save(grupo));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupo);
	}

	@PutMapping("/{grupoId}")
	public ResponseEntity<GrupoModel> update(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupoAtual = grupoService.findById(grupoId);
		grupoConverter.copyPropetiesToDomain(grupoInput, grupoAtual);
		GrupoModel grupoAtualizado = grupoConverter.toModel(grupoService.save(grupoAtual));
		
		return ResponseEntity.ok(grupoAtualizado);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long grupoId) {
		grupoService.delete(grupoId);
	}
}
