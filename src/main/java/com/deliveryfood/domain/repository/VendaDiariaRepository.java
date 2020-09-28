package com.deliveryfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.repository.custom.VendaDiariaCustomRepository;

@Repository
public interface VendaDiariaRepository extends JpaRepository<Pedido, Long>, VendaDiariaCustomRepository {

}
