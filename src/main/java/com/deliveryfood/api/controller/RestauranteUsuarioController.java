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

import com.deliveryfood.api.converter.UsuarioConverter;
import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@GetMapping
	public List<UsuarioModel> findAll(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		return usuarioConverter.toCollectionModel(restaurante.getUsuarios());
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
