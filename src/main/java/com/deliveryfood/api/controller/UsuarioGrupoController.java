package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToUsuarioGrupoDesvinculacao;

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

import com.deliveryfood.api.assembler.GrupoAssembler;
import com.deliveryfood.api.assembler.hateaos.LinkAssembler;
import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoAssembler grupoAssembler;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoModel> findAll(@PathVariable Long usuarioId) {
	    Usuario usuario = usuarioService.findById(usuarioId);
	    
	    CollectionModel<GrupoModel> gruposModel = grupoAssembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks()
	            .add(LinkAssembler.linkToUsuarioGrupoVinculacao(usuarioId, "vincular"));
	    
	    gruposModel.getContent().forEach(grupoModel -> {
	        grupoModel.add(linkToUsuarioGrupoDesvinculacao(usuarioId, grupoModel.getId(), "desvincular"));
	    });
	    
	    return gruposModel;
	}    
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> vincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.vincularGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> desvincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desvincularGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
}
