package com.deliveryfood.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.domain.model.Pedido;

@Component
public class PedidoConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }
    
    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
    
    public Pedido toDomain(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
    
}
