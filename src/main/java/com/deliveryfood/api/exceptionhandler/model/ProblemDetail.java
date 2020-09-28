package com.deliveryfood.api.exceptionhandler.model;

import lombok.Getter;

@Getter
public enum ProblemDetail {
	
	VALOR_TIPO_INVALIDO("A propriedade '%s' recebeu o valor '%s', "
			+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s."),
	PROPRIEDADE_INEXISTENTE("A propriedade '%s' não existe. "
			+ "Corrija ou remova essa propriedade e tente novamente."),
	CORPO_INVALIDO("O corpo da requisição está inválido. Verifique erro de sintaxe."),
	PARAMETRO_URL_INVALIDO("O parâmetro de URL '%s' recebeu o valor '%s', "
			+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s."),
	RECURSO_INEXISTENTE("O recurso '%s' não existe."),
	ERRO_INESPERADO("Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, "
				+ "entre em contato com administrador do sistema."),
	DADOS_INVALIDOS("Os dados informados estão inválidos, por favor revise as informações.");
	
	
	private String detail;
	
	ProblemDetail(String detail) {
		this.detail = detail;
	}
	

}
