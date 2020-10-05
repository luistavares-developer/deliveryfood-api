package com.deliveryfood.core.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.deliveryfood.core.storage.config.StorageProperties;
import com.deliveryfood.core.storage.exception.StorageException;

public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public FotoRecuperada recuperar(String nomeFoto) {
		try {
			Path diretorioDestino = getPathDestino(nomeFoto);
			FotoRecuperada fotoRecuperada = new FotoRecuperada.FotoRecuperadaBuilder()
					.inputStream(Files.newInputStream(diretorioDestino)).build();
			
			return fotoRecuperada;
			
		} catch (IOException ex) {
			throw new StorageException("Não foi possivel recuperar o arquivo", ex);
		}
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path pathDestino = getPathDestino(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(pathDestino));
		} catch (IOException ex) {
			throw new StorageException("Não foi possivel armazenar o arquivo", ex);
		}
	}
	
	@Override
	public void remover(String nomeFotoAntiga) {
		try {
			Path diretorioDestino = getPathDestino(nomeFotoAntiga);
			Files.deleteIfExists(diretorioDestino);
		} catch (IOException ex) {
			throw new StorageException("Não foi possivel remover o arquivo", ex);
		}
		
	}

	private Path getPathDestino(String nomeArquivo) {
		return storageProperties.getLocal().getRepositorioFotos().resolve(Path.of(nomeArquivo));
	}
}
