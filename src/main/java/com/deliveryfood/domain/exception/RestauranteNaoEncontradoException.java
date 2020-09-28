package com.deliveryfood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradoException(Long restauranteId) {
		super(String.format("Restaurante com id %d não foi encontrado", restauranteId));
	}
}
