package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToFotoProduto;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToProduto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.FotoProdutoModel;
import com.deliveryfood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FotoProdutoModel toModel(FotoProduto fotoProduto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);

		fotoProdutoModel.add(linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

		fotoProdutoModel.add(linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));

		return fotoProdutoModel;
	}
}
