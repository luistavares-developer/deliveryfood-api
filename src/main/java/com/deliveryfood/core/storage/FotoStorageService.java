package com.deliveryfood.core.storage;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	FotoRecuperada recuperar(String nomeFoto);
	
	void armazenar(NovaFoto novaFoto);

	void remover(String nomeFotoAntiga);
	
	default void substituir(String nomeFotoAntiga, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeFotoAntiga != null) {
			this.remover(nomeFotoAntiga);
		}
	}
	
	default String gerarNomeArquivo(String nomeOrginal) {
		return UUID.randomUUID().toString() + "_" + nomeOrginal;
	}
	
	@Builder
	@Getter
	class NovaFoto {
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
		private Long tamanho;
	}
	
	@Builder
	@Getter
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return this.url != null;
		}
	}
}
