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
import com.deliveryfood.api.controller.EstatisticasController;
import com.deliveryfood.api.controller.FormaPagamentoController;
import com.deliveryfood.api.controller.GrupoController;
import com.deliveryfood.api.controller.GrupoPermissaoController;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.controller.PermissaoController;
import com.deliveryfood.api.controller.RestauranteController;
import com.deliveryfood.api.controller.RestauranteFormasPagamentoController;
import com.deliveryfood.api.controller.RestauranteProdutoController;
import com.deliveryfood.api.controller.RestauranteProdutoFotoController;
import com.deliveryfood.api.controller.RestauranteUsuarioController;
import com.deliveryfood.api.controller.UsuarioController;
import com.deliveryfood.api.controller.UsuarioGrupoController;

@Component
public class LinkAssembler {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));   
	
	private LinkAssembler() {};
	
	public static Link linkToPedidos(String rel) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl, 
				PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
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
	    String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();

	    return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
	}
	
	public static Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToAbrirRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
	}
	
	public static Link linkToFecharRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
	}
	
	public static Link linkToAtivarRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
	}
	
	public static Link linkToInativarRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
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
	
	public static Link linkToUsuarioGrupoVinculacao(Long usuarioId, String rel) {
	    return linkTo(methodOn(UsuarioGrupoController.class)
	            .vincularGrupo(usuarioId, null)).withRel(rel);
	}

	public static Link linkToUsuarioGrupoDesvinculacao(Long usuarioId, Long grupoId, String rel) {
	    return linkTo(methodOn(UsuarioGrupoController.class)
	            .desvincularGrupo(usuarioId, grupoId)).withRel(rel);
	}   
	
	public static Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioController.class)
				.findAll(restauranteId)).withRel(rel);
	}
	
	public static Link linkToVincularUsuarioAoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioController.class).vincularUsuario(restauranteId, null)).withRel(rel);
	}
	
	public static Link linkToDesvincularUsuarioDoRestaurante(Long restauranteId, Long usuarioId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioController.class).desvincularUsuario(restauranteId, usuarioId)).withRel(rel);
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
	
	public static Link linkToRestauranteFormasPagamento(Long restauranteId) {
	    return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToVincularFormaPagamentoDeRestaurante(Long restauranteId, String rel) {
		return linkTo(
				 methodOn(RestauranteFormasPagamentoController.class).vincularFormaPagamento(restauranteId, null))
				.withRel(rel);
	}
	
	public static Link linkToDesvincularFormaPagamentoDeRestaurante(Long restauranteId, Long formaPagamentoId, String rel) {
		return linkTo(
				 methodOn(RestauranteFormasPagamentoController.class).desvincularFormaPagamento(restauranteId, formaPagamentoId))
				.withRel(rel);
	}

	public static Link linkToFormasPagamento(String rel) {
	    return linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public static Link linkToFormasPagamento() {
	    return linkToFormasPagamento(IanaLinkRelations.SELF.value());
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
	
	public static Link linkToProdutos(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteProdutoController.class)
	            .findById(restauranteId, null)).withRel(rel);
	}

	public static Link linkToProdutos(Long restauranteId) {
	    return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public static Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
	    return linkTo(methodOn(RestauranteProdutoFotoController.class)
	            .findDadosFoto(restauranteId, produtoId)).withRel(rel);
	}

	public static Link linkToFotoProduto(Long restauranteId, Long produtoId) {
	    return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
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
	
	public static Link linkToGrupos(String rel) {
	    return linkTo(GrupoController.class).withRel(rel);
	}

	public static Link linkToGrupos() {
	    return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public static Link linkToGrupoPermissoes(Long grupoId, String rel) {
	    return linkTo(methodOn(GrupoPermissaoController.class)
	            .findAll(grupoId)).withRel(rel);
	}     
	
	public static Link linkToPermissoes(String rel) {
	    return linkTo(PermissaoController.class).withRel(rel);
	}

	public static Link linkToPermissoes() {
	    return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public static Link linkToGrupoPermissoes(Long grupoId) {
	    return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToGrupoPermissaoVinculacao(Long grupoId, String rel) {
	    return linkTo(methodOn(GrupoPermissaoController.class)
	            .vincularPermissao(grupoId, null)).withRel(rel);
	}

	public static Link linkToGrupoPermissaoDesvinculacao(Long grupoId, Long permissaoId, String rel) {
	    return linkTo(methodOn(GrupoPermissaoController.class)
	            .desvincularPermissao(grupoId, permissaoId)).withRel(rel);
	}
	
	public static Link linkToEstatisticas(String rel) {
	    return linkTo(EstatisticasController.class).withRel(rel);
	}

	public static Link linkToEstatisticasVendasDiarias(String rel) {
	    TemplateVariables filtroVariables = new TemplateVariables(
	            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
	            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	    
	    String pedidosUrl = linkTo(methodOn(EstatisticasController.class)
	            .consultarVendasDiarias(null, null)).toUri().toString();
	    
	    return new Link(UriTemplate.of(pedidosUrl, filtroVariables), rel);
	}      
	
	
}
