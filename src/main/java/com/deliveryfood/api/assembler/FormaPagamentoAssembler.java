package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToFormasPagamento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.FormaPagamentoController;
import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.model.input.FormaPagamentoInput;
import com.deliveryfood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);

		modelMapper.map(formaPagamento, formaPagamentoModel);

		formaPagamentoModel.add(linkToFormasPagamento("formasPagamento"));

		return formaPagamentoModel;
	}

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(linkToFormasPagamento());
    } 

	public FormaPagamento toDomain(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}

	public void copyPropetiesToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
}
