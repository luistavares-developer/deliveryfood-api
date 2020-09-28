package com.deliveryfood.domain.repository.custom.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.custom.UsuarioCustomRepository;

@Repository
public class UsuarioCustomRepositoryImpl implements UsuarioCustomRepository {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void dessincronizar(Usuario usuario) {
		entityManager.detach(usuario);
	}

}
