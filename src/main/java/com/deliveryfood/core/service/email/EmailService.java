package com.deliveryfood.core.service.email;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	public class Mensagem {
		
		@Singular
		@NonNull
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular
		private Map<String, Object> campos;
	}
}
