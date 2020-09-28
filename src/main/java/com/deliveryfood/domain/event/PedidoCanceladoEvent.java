package com.deliveryfood.domain.event;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.deliveryfood.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent extends AbstractAggregateRoot<Pedido> {

	private Pedido pedido;
}
