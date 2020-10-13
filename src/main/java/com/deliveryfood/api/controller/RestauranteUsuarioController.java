package com.deliveryfood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.UsuarioAssembler;
import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioAssembler usuarioAssembler;
	
	@GetMapping
	public CollectionModel<UsuarioModel> findAll(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		CollectionModel<UsuarioModel> usuarios = usuarioAssembler.toCollectionModel(restaurante.getUsuarios());
		
		if(restaurante.getUsuarios().isEmpty()) {
			usuarios.removeLinks();
		} else {
			usuarios.removeLinks().add(linkTo(
					methodOn(RestauranteUsuarioController.class).findAll(restauranteId)
					).withSelfRel());
		}
		
		return usuarios;
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.vincularUsuario(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desvincularUsuario(restauranteId, usuarioId);
	}
}
