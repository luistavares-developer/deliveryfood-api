package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCidades;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCozinhas;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToEstados;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToEstatisticas;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToFormasPagamento;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupos;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToPedidos;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToPermissoes;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestaurantes;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToUsuarios;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(linkToPedidos("pedidos"));
		rootEntryPointModel.add(linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(linkToGrupos("grupos"));
		rootEntryPointModel.add(linkToUsuarios("usuarios"));
		rootEntryPointModel.add(linkToPermissoes("permissoes"));
		rootEntryPointModel.add(linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(linkToEstados("estados"));
		rootEntryPointModel.add(linkToCidades("cidades"));
		rootEntryPointModel.add(linkToEstatisticas("estatisticas"));

		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}