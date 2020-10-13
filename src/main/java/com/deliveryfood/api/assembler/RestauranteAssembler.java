package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCidade;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToCozinha;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestauranteFormasPagamento;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestauranteResponsaveis;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToRestaurantes;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.RestauranteController;
import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	public RestauranteAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(
                linkToCozinha(restaurante.getCozinha().getId()));
        
        if(restaurante.getEndereco() != null) {
        	restauranteModel.getEndereco().getCidade().add(
        			linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
        
        restauranteModel.add(linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(linkToRestauranteResponsaveis(restaurante.getId(), 
                "responsaveis"));
        
        return restauranteModel;
        
	}
	
	@Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkToRestaurantes());
    }   
	
	public Restaurante toDomain(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyPropetiesToDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// Cozinha ou Cidade was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
}
