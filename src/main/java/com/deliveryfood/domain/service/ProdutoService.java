package com.deliveryfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.ProdutoNaoEncontradoException;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	public Produto findById(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId )
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
	public List<Produto> findByRestaurante(Restaurante restaurante) {
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	public List<Produto> findAtivosByRestaurante(Restaurante restaurante) {
		return produtoRepository.findAtivosByRestaurante(restaurante);
	}
	
	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
}
