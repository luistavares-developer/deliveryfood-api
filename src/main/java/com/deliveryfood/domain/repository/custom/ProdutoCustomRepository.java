package com.deliveryfood.domain.repository.custom;

import com.deliveryfood.domain.model.FotoProduto;

public interface ProdutoCustomRepository {

	FotoProduto save(FotoProduto foto);
	
	void delete(FotoProduto foto);
}
