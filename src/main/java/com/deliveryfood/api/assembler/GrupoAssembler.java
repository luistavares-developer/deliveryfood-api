package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupoPermissoes;
import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToGrupos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.GrupoController;
import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.model.Grupo;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }
	
	@Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        grupoModel.add(linkToGrupos("grupos"));
        
        grupoModel.add(linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        
        return grupoModel;
    }
    
    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(linkToGrupos());
    }            
	
	public Grupo toDomain(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyPropetiesToDomain(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
}
