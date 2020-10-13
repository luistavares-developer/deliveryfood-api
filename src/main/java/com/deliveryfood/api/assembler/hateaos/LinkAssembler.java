package com.deliveryfood.api.assembler.hateaos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.CidadeController;
import com.deliveryfood.api.controller.CozinhaController;
import com.deliveryfood.api.controller.EstadoController;
import com.deliveryfood.api.controller.FormaPagamentoController;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.controller.RestauranteController;
import com.deliveryfood.api.controller.RestauranteFormasPagamentoController;
import com.deliveryfood.api.controller.RestauranteProdutoController;
import com.deliveryfood.api.controller.RestauranteUsuarioController;
import com.deliveryfood.api.controller.UsuarioController;
import com.deliveryfood.api.controller.UsuarioGrupoController;

@Component
public class LinkAssembler {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	private LinkAssembler() {};
	
	public static Link linkToPedidos() {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl, 
				PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
	}
	
	public static Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class)
				.confirmar(codigoPedido)).withRel(rel);
	}
	
	public static Link linkToEntregaPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class)
				.entregar(codigoPedido)).withRel(rel);
	}
	
	public static Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class)
				.cancelar(codigoPedido)).withRel(rel);
	}
	
	public static Link linkToRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class)
				.findById(restauranteId)).withRel(rel);
	}
	
	public static Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToRestaurantes(String rel) {
		return linkTo(RestauranteController.class).withRel(rel);
	}
	
	public static Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormasPagamentoController.class)
				.findAll(restauranteId)).withRel(rel);
	}
	
	public static Link linkToUsuario(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioController.class)
				.findById(usuarioId)).withRel(rel);
	}
	
	public static Link linkToUsuario(Long usuarioId) {
		return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToUsuarios(String rel) {
		return linkTo(UsuarioController.class).withRel(rel);
	}
	
	public static Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToGruposUsuario(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class)
				.findAll(usuarioId)).withRel(rel);
	}
	
	public static Link linkToGruposUsuario(Long usuarioId) {
		return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioController.class)
				.findAll(restauranteId)).withRel(rel);
	}
	
	public static Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.findById(formaPagamentoId)).withRel(rel);
	}
	
	public static Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToCidade(Long cidadeId, String rel) {
		return linkTo(methodOn(CidadeController.class)
				.findById(cidadeId)).withRel(rel);
	}
	
	public static Link linkToCidade(Long cidadeId) {
		return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToCidades(String rel) {
		return linkTo(CidadeController.class).withRel(rel);
	}
	
	public static Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToEstado(Long estadoId, String rel) {
		return linkTo(methodOn(EstadoController.class)
				.findById(estadoId)).withRel(rel);
	}
	
	public static Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToEstados(String rel) {
		return linkTo(EstadoController.class).withRel(rel);
	}
	
	public static Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class)
				.findById(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public static Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToCozinhas(String rel) {
		return linkTo(CozinhaController.class).withRel(rel);
	}
	
	public static Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToCozinha(Long cozinhaId, String rel) {
		return linkTo(methodOn(CozinhaController.class)
				.findById(cozinhaId)).withRel(rel);
	}
	
	public static Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}
	
	
}
