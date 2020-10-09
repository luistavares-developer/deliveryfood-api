package com.deliveryfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.CidadeController;
import com.deliveryfood.api.controller.EstadoController;
import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.api.model.input.CidadeInput;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Estado;

@Component
public class CidadeAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	public CidadeAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
		
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(linkTo(
				methodOn(CidadeController.class).findAll()
				).withRel("cidades"));
		
		cidadeModel.getEstado().add(linkTo(
				methodOn(EstadoController.class).findById(cidade.getEstado().getId())
				).withSelfRel());
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(linkTo(CidadeController.class).withSelfRel());
	}
	
	public Cidade toDomain(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyPropetiesToDomain(CidadeInput cidadeInput, Cidade cidade) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// Estado was altered from 1 to 2
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
}
