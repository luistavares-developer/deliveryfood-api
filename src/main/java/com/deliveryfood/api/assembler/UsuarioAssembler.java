
package com.deliveryfood.api.assembler;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToUsuarios;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.deliveryfood.api.controller.UsuarioController;
import com.deliveryfood.api.controller.UsuarioGrupoController;
import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.model.input.UsuarioInput;
import com.deliveryfood.domain.model.Usuario;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {


	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		
		usuarioModel.add(linkTo(
				methodOn(UsuarioController.class).findAll()
				).withRel("usuarios"));
		
		usuarioModel.add(linkTo(
				methodOn(UsuarioGrupoController.class).findAll(usuario.getId())
				).withRel("grupos-usuario"));
		
		return usuarioModel;
	}
	
	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
	    return super.toCollectionModel(entities)
	        .add(linkToUsuarios());
	}
	
	public Usuario toDomain(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyPropetiesToDomain(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
