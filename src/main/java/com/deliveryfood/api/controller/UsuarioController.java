package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.UsuarioConverter;
import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.model.input.SenhaInput;
import com.deliveryfood.api.model.input.UsuarioComSenhaInput;
import com.deliveryfood.api.model.input.UsuarioInput;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioConverter usuarioConverter;

	@GetMapping
	public List<UsuarioModel> findAll() {
		
		return usuarioConverter.toCollectionModel(usuarioService.findAll());
	}

	@GetMapping("/{usuarioId}")
	public UsuarioModel findById(@PathVariable Long usuarioId) {
		
		return usuarioConverter.toModel(usuarioService.findById(usuarioId));
	}

	@PostMapping
	public ResponseEntity<UsuarioModel> save(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		
		Usuario usuario = usuarioConverter.toDomain(usuarioInput);
		UsuarioModel novoUsuario = usuarioConverter.toModel(usuarioService.save(usuario));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<UsuarioModel> update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		
		Usuario usuarioAtual = usuarioService.findById(usuarioId);
		usuarioConverter.copyPropetiesToDomain(usuarioInput, usuarioAtual);
		UsuarioModel usuarioAtualizado = usuarioConverter.toModel(usuarioService.save(usuarioAtual));
		
		return ResponseEntity.ok(usuarioAtualizado);
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {

		usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

}
