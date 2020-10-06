package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.GrupoConverter;
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
	private GrupoConverter grupoConverter;
	
	@GetMapping
	public List<GrupoModel> findAll(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.findById(usuarioId);
		return grupoConverter.toCollectionModel(usuario.getGrupos());
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.vincularGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desvincularGrupo(usuarioId, grupoId);
	}
}
