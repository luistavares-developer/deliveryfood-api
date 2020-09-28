package com.deliveryfood.domain.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.repository.custom.ProdutoCustomRepository;

@Repository
public class ProdutoCustomRepositoryImpl implements ProdutoCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return entityManager.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto foto) {
		entityManager.remove(foto);
	}
}
