package com.deliveryfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO,
	CONFIRMADO(CRIADO),
	ENTREGUE(CONFIRMADO),
	CANCELADO(CRIADO);
	
	private List<StatusPedido> statusAnterior;
	
	private StatusPedido(StatusPedido... statusAnteriores) {
		this.statusAnterior = Arrays.asList(statusAnteriores);
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnterior.contains(this);
	}
	
	public boolean PodeAlterarPara(StatusPedido novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}
}