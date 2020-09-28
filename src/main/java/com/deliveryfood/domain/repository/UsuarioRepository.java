package com.deliveryfood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.custom.UsuarioCustomRepository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioCustomRepository {

	Optional<Usuario> findByEmail(String email);
}
