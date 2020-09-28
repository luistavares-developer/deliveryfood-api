package com.deliveryfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.api.model.EnderecoModel;
import com.deliveryfood.api.model.input.ItemPedidoInput;
import com.deliveryfood.domain.model.Endereco;
import com.deliveryfood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
  		.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		
		TypeMap<Endereco, EnderecoModel> typeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		typeMap.<String>addMapping(enderecoOrigem -> enderecoOrigem.getCidade().getEstado().getNome(),
				(enderecoModelDestino, valor) -> enderecoModelDestino.getCidade().setEstado(valor));
		

		return modelMapper;
	}
}
