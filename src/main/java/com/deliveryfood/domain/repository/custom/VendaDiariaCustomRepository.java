package com.deliveryfood.domain.repository.custom;

import java.util.List;

import com.deliveryfood.domain.model.VendaDiaria;
import com.deliveryfood.domain.repository.filter.VendaDiariaFilter;

public interface VendaDiariaCustomRepository {

	List<VendaDiaria> buscaVendasDiaria(VendaDiariaFilter filter, String timeOffSet);
}
