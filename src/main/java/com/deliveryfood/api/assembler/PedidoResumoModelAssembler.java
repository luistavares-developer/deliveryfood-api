package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToPedidos;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestaurante;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToUsuario;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.PedidoController;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel>{

	@Autowired
    private ModelMapper modelMapper;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}
    
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);
        
        pedidoResumoModel.add(linkToPedidos());
        
        pedidoResumoModel.getRestaurante().add(linkToRestaurante(pedidoResumoModel.getRestaurante().getId()));
        
        pedidoResumoModel.getCliente().add(linkToUsuario(pedidoResumoModel.getCliente().getId()));
        
    	return pedidoResumoModel;
    }
    
    @Override
    public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
    	return super.toCollectionModel(entities).add(linkTo(PedidoController.class).withSelfRel());
    }
    
}
