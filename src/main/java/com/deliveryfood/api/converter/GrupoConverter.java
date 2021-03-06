package com.deliveryfood.api.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.model.Grupo;

@Component
public class GrupoConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoModel.class);
	}
	
	public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos	) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}
	
	public Grupo toDomain(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyPropetiesToDomain(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
}
