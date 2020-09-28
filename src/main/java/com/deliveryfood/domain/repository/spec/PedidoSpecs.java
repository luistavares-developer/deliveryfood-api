package com.deliveryfood.domain.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {

	public static Specification<Pedido> filter(PedidoFilter filter) {
		return (root, query, builder) -> {
		//Essa validação é por conta do count realizado para paginação
		if(Pedido.class.equals(query.getResultType())) {
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
		}
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getClienteId() != null) {
			predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
		}
		
		if(filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
		}
		
		if(filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
		}
		
		return builder.and(predicates.toArray(new Predicate[0]));
		
		};
	}
}
