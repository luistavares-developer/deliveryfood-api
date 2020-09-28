package com.deliveryfood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
		super(String.format("Forma de pagamento com id %d não foi encontrado", formaPagamentoId));
	}
}
