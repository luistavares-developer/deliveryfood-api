package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToFotoProduto;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToProdutos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.RestauranteProdutoController;
import com.deliveryfood.api.model.ProdutoModel;
import com.deliveryfood.api.model.input.ProdutoInput;
import com.deliveryfood.domain.model.Produto;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
	}

	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

		modelMapper.map(produto, produtoModel);

		produtoModel.add(linkToProdutos(produto.getRestaurante().getId(), "produtos"));

		produtoModel.add(linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));

		return produtoModel;
	}

	public Produto toDomain(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}

	public void copyPropetiesToDomain(ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}
}
