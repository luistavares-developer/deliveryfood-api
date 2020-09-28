package com.deliveryfood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		super(String.format("Foto do produto %d do restaurante %d não foi encontrada", produtoId, restauranteId));
	}	
}