package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupoPermissaoDesvinculacao;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupoPermissaoVinculacao;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupoPermissoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.PermissaoAssembler;
import com.deliveryfood.api.model.PermissaoModel;
import com.deliveryfood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoAssembler permissaoAssembler;
	
	@Override
	@GetMapping
	public CollectionModel<PermissaoModel> findAll(@PathVariable Long grupoId) {
	    Grupo grupo = grupoService.findById(grupoId);
	    
	    CollectionModel<PermissaoModel> permissoesModel 
	        = permissaoAssembler.toCollectionModel(grupo.getPermissoes())
	            .removeLinks()
	            .add(linkToGrupoPermissoes(grupoId))
	            .add(linkToGrupoPermissaoVinculacao(grupoId, "vincular"));
	    
	    permissoesModel.getContent().forEach(permissaoModel -> {
	        permissaoModel.add(linkToGrupoPermissaoDesvinculacao(grupoId, permissaoModel.getId(), "desvincular"));
	    });
	    
	    return permissoesModel;
	}    
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> vincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.vincularPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desvincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desvincularPermissao(grupoId, permissaoId);
	
		return ResponseEntity.noContent().build();
	}

}
