package com.deliveryfood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.deliveryfood.api.assembler.CozinhaAssembler;
import com.deliveryfood.api.model.CozinhaModel;
import com.deliveryfood.api.model.input.CozinhaInput;
import com.deliveryfood.api.openapi.controller.CozinhaControllerOpenApi;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.service.CozinhaService;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaAssembler cozinhaAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedModelAssembler;

	@GetMapping
	public PagedModel<CozinhaModel> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaService.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhaPagedModel = pagedModelAssembler.toModel(cozinhasPage, cozinhaAssembler);
		
		return cozinhaPagedModel;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel findById(@PathVariable Long cozinhaId) {
		
		return cozinhaAssembler.toModel(cozinhaService.findById(cozinhaId));
	}

	@PostMapping
	public ResponseEntity<CozinhaModel> save(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaAssembler.toDomain(cozinhaInput);
		CozinhaModel novaCozinha = cozinhaAssembler.toModel(cozinhaService.save(cozinha));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<CozinhaModel> update(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinhaAtual = cozinhaService.findById(cozinhaId);
		cozinhaAssembler.copyPropetiesToDomain(cozinhaInput, cozinhaAtual);
		CozinhaModel cozinhaAtualizada = cozinhaAssembler.toModel(cozinhaService.save(cozinhaAtual));
		
		return ResponseEntity.ok(cozinhaAtualizada);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cozinhaId) {
		cozinhaService.delete(cozinhaId);
	}

}
