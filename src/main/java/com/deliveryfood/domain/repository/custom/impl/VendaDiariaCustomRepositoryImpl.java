package com.deliveryfood.domain.repository.custom.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.StatusPedido;
import com.deliveryfood.domain.model.VendaDiaria;
import com.deliveryfood.domain.repository.custom.VendaDiariaCustomRepository;
import com.deliveryfood.domain.repository.filter.VendaDiariaFilter;

@Repository
public class VendaDiariaCustomRepositoryImpl implements VendaDiariaCustomRepository {

	private static final String UTC = "+00:00";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<VendaDiaria> buscaVendasDiaria(VendaDiariaFilter filter, String timeOffSet) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
		Root<Pedido> root = query.from(Pedido.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
		}
		
		if(filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		Expression<Date> functionConvertTz = builder.function("convert_tz", Date.class, 
				root.get("dataCriacao"), builder.literal(UTC), builder.literal(timeOffSet));
		
		Expression<Date> functionDate = builder.function("date", Date.class, functionConvertTz);
		
		CompoundSelection<VendaDiaria> select = builder.construct(VendaDiaria.class, 
																  functionDate, 
																  builder.count(root.get("id")), 
																  builder.sum(root.get("valorTotal")));
		query.select(select);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDate);
		
		return entityManager.createQuery(query).getResultList();
	}
	
}
