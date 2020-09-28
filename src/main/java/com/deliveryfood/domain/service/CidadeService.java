package com.deliveryfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.CidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EstadoNaoEncontradoException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	private static final String ESTADO_NAO_CADASTRADO = "O estado com id %d não foi cadastrado";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;

	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	public Cidade findById(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}

	@Transactional
	public Cidade save(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		try {
			Estado estado = estadoService.findById(estadoId);
			cidade.setEstado(estado);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(String.format(ESTADO_NAO_CADASTRADO, estadoId));
		}

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void delete(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(CIDADE_EM_USO, cidadeId));
		}
	}
}
