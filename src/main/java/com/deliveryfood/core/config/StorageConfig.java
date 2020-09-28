package com.deliveryfood.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.deliveryfood.core.config.StorageProperties.TipoStorage;
import com.deliveryfood.core.service.storage.FotoStorageService;
import com.deliveryfood.core.service.storage.LocalFotoStorageService;
import com.deliveryfood.core.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	public AmazonS3 amazonS3Factory() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(storageProperties.getS3()
				.getIdChaveAcesso(), storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if(TipoStorage.S3.equals(storageProperties.getTipoStorage())) {
			return new S3FotoStorageService();
		}
		return new LocalFotoStorageService();
	}
}
