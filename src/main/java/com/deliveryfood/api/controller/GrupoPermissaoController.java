package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.PermissaoConverter;
import com.deliveryfood.api.model.PermissaoModel;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoConverter permissaoConverter;
	
	@GetMapping
	public List<PermissaoModel> findAll(@PathVariable Long grupoId) {

		Grupo grupo = grupoService.findById(grupoId);
		return permissaoConverter.toCollectionModel(grupo.getPermissoes());
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.vincularPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desvincularPermissao(grupoId, permissaoId);
	}

}
