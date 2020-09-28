package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.UsuarioNaoEncontradoException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final String SENHA_INVALIDA = "Senha atual informada não coincide com a senha do usuário.";

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Usuario findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario save(Usuario usuario) {
		
		usuarioRepository.dessincronizar(usuario);
		
		validaEmailDuplicado(usuario);
		
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuarioAtual = this.findById(usuarioId);
		if(usuarioAtual.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException(SENHA_INVALIDA);
		}
		
		usuarioAtual.setSenha(novaSenha);
	}
	
	@Transactional
	public void vincularGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = this.findById(usuarioId);
		Grupo grupo = grupoService.findById(grupoId);
		usuario.vincularGrupo(grupo);
	}
	
	@Transactional
	public void desvincularGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = this.findById(usuarioId);
		Grupo grupo = grupoService.findById(grupoId);
		usuario.desvincularGrupo(grupo);
	}
	
	private void validaEmailDuplicado(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(emailExiste(usuario, usuarioExistente)) {
			throw new NegocioException(String.format("O e-mail %s já existe, por favor utilize outro e-mail.", usuario.getEmail()));
		}
	}

	private boolean emailExiste(Usuario usuario, Optional<Usuario> usuarioExistente) {
		return usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario);
	}
}
