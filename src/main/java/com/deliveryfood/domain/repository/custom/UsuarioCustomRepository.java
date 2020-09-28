package com.deliveryfood.domain.repository.custom;

import com.deliveryfood.domain.model.Usuario;

public interface UsuarioCustomRepository {

	public void dessincronizar(Usuario usuario);
}
