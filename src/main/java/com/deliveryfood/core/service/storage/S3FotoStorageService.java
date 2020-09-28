package com.deliveryfood.core.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.deliveryfood.core.config.StorageProperties;
import com.deliveryfood.core.service.exception.StorageException;

public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public FotoRecuperada recuperar(String nomeFoto) {
		try {
			String pathDestino = getPathDestino(nomeFoto);
			URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), pathDestino);
			return new FotoRecuperada.FotoRecuperadaBuilder().url(url.toString()).build();
			
		} catch (Exception ex) {
			throw new StorageException("Não foi possivel buscar a url da foto", ex);
		}
		
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String pathDestino = getPathDestino(novaFoto.getNomeArquivo());
			ObjectMetadata metadados = new ObjectMetadata();
			metadados.setContentType(novaFoto.getContentType());
			metadados.setContentLength(novaFoto.getTamanho());

			PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), pathDestino,
					novaFoto.getInputStream(), metadados).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);

		} catch (Exception ex) {
			throw new StorageException("Não foi possivel armazenar o arquivo no S3.", ex);
		}
	}

	@Override
	public void remover(String nomeFotoAntiga) {
		try {
			String pathDestino = getPathDestino(nomeFotoAntiga);
			
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
					storageProperties.getS3().getBucket(), pathDestino);
			
			amazonS3.deleteObject(deleteObjectRequest);
			
		} catch (Exception ex) {
			throw new StorageException("Não foi possivel excluir o arquivo no S3", ex);
		}
	}

	private String getPathDestino(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getRepositorioFotos(), nomeArquivo);
	}

}
