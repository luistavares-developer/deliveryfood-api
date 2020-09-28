package com.deliveryfood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.core.service.storage.FotoStorageService;
import com.deliveryfood.core.service.storage.FotoStorageService.NovaFoto;
import com.deliveryfood.domain.exception.FotoProdutoNaoEncontradoException;
import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	public FotoProduto findDadosFoto(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
	@Transactional
	public FotoProduto save(FotoProduto foto, InputStream dadosFoto) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeFotoExistente = null;
		
		Optional<FotoProduto> fotoAtualOptional = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoAtualOptional.isPresent()) {
			nomeFotoExistente = fotoAtualOptional.get().getNomeArquivo();
			produtoRepository.delete(fotoAtualOptional.get());
		}
		
		foto.setNomeArquivo(nomeArquivo);
		
		FotoProduto fotoProduto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(nomeArquivo)
											  .contentType(foto.getContentType())
											  .tamanho(foto.getTamanho())
											  .inputStream(dadosFoto).build();
		
		fotoStorageService.substituir(nomeFotoExistente, novaFoto);
		
		return fotoProduto;
	}
	
	@Transactional
	public void delete(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = this.findDadosFoto(restauranteId, produtoId);
		
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		
		fotoStorageService.remover(fotoProduto.getNomeArquivo());
	}
}
