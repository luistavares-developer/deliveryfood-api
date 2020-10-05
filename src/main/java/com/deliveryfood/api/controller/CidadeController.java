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

import com.deliveryfood.api.converter.CidadeConverter;
import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.api.model.input.CidadeInput;
import com.deliveryfood.api.openapi.controller.CidadeControllerOpenApi;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeConverter cidadeConverter;
	
	@GetMapping
	public List<CidadeModel> findAll() {
		
		return cidadeConverter.toCollectionModel(cidadeService.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel findById(@PathVariable Long cidadeId) {
		
		return cidadeConverter.toModel(cidadeService.findById(cidadeId));
	}

	@PostMapping
	public ResponseEntity<CidadeModel> save(@RequestBody @Valid CidadeInput cidadeInput) {
		
		Cidade cidade = cidadeConverter.toDomain(cidadeInput);
		CidadeModel novaCidade = cidadeConverter.toModel(cidadeService.save(cidade));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCidade);
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<CidadeModel> update(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		
		Cidade cidadeAtual = cidadeService.findById(cidadeId);
		cidadeConverter.copyPropetiesToDomain(cidadeInput, cidadeAtual);
		CidadeModel cidadeAtualizada = cidadeConverter.toModel(cidadeService.save(cidadeAtual));
		
		return ResponseEntity.ok(cidadeAtualizada);
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cidadeId) {
		cidadeService.delete(cidadeId);
	}

}
