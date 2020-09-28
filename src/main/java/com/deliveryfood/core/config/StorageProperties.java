package com.deliveryfood.core.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("storage")
public class StorageProperties {

	private S3 s3 = new S3();
	private Local local = new Local();
	private TipoStorage tipoStorage = TipoStorage.LOCAL;
	
	public enum TipoStorage {
		LOCAL,
		S3
	}
			
	
	@Setter
	@Getter
	public class Local {
		Path repositorioFotos;
	}
	
	@Setter
	@Getter
	public class S3 {
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private Regions regiao;
		private String repositorioFotos;
	}
}
