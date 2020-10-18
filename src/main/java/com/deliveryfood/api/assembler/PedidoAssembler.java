package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCancelamentoPedido;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCidade;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToConfirmacaoPedido;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToEntregaPedido;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToProduto;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestaurante;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.assembler.hateaos.LinkAssembler;
import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(LinkAssembler.linkToPedidos("pedidos"));

		pedidoModel.getRestaurante().add(linkToRestaurante(pedidoModel.getRestaurante().getId()));

		pedidoModel.getCliente().add(LinkAssembler.linkToUsuario(pedidoModel.getCliente().getId()));

		pedidoModel.getFormaPagamento()
				.add(LinkAssembler.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));

		pedidoModel.getEnderecoEntrega().getCidade()
				.add(linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));

		pedidoModel.getItens().forEach(item -> {
			item.add(linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId()));
		});
		
		if(pedido.podeSerConfirmado()) {
			pedidoModel.add(linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
		}
		
		if(pedido.podeSerEntregue()) {
			pedidoModel.add(linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
		}
		
		if(pedido.podeSerCancelado()) {
			pedidoModel.add(linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));
		}

		return pedidoModel;
	}

	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities).add(linkTo(PedidoController.class).withSelfRel());
	}

	public Pedido toDomain(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}

}
