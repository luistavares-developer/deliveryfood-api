package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToResponsaveisRestaurante;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToVincularUsuarioAoRestaurante;

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

import com.deliveryfood.api.assembler.UsuarioAssembler;
import com.deliveryfood.api.assembler.hateaos.LinkAssembler;
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
			usuarios.removeLinks()
					.add(linkToResponsaveisRestaurante(restauranteId))
					.add(linkToVincularUsuarioAoRestaurante(restauranteId, "vincular"));
			
			usuarios.getContent().forEach(usuario -> {
				usuario.add(LinkAssembler.linkToDesvincularUsuarioDoRestaurante(restaurante.getId(), usuario.getId(), "desvincular"));
			});
		}
		
		return usuarios;
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> vincularUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.vincularUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> desvincularUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desvincularUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();

	}
}
