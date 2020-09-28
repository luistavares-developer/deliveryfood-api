package com.deliveryfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.GrupoNaoEncontradoException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.model.Permissao;
import com.deliveryfood.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private static final String GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}
	
	public Grupo findById(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public Grupo save(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void delete(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(GRUPO_EM_USO, grupoId));
		}
	}
	
	@Transactional
	public void vincularPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = this.findById(grupoId);
		Permissao permissao = permissaoService.findById(permissaoId);
		grupo.vincularPermissao(permissao);
	}
	
	@Transactional
	public void desvincularPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = this.findById(grupoId);
		Permissao permissao = permissaoService.findById(permissaoId);
		grupo.desvincularPermissao(permissao);
	}
	
}
