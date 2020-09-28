package com.deliveryfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.CidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.CozinhaNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.RestauranteNaoEncontradoException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<Restaurante> findAll(){
		return restauranteRepository.findAll();
	}
	
	public Restaurante findById(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public Restaurante save(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		try{
			Cozinha cozinha = cozinhaService.findById(cozinhaId);
			Cidade cidade = cidadeService.findById(cidadeId);
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		} 
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = this.findById(restauranteId);
		restaurante.ativar();
	}
	
	@Transactional
	public void ativarLote(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::ativar);
		} catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = this.findById(restauranteId);
		restaurante.inativar();
	}
	
	@Transactional
	public void inativarLote(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::inativar);
		} catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = this.findById(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = this.findById(restauranteId);
		restaurante.fechar();
	}
	
	@Transactional
	public void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = this.findById(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);	
		
		restaurante.vincularFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = this.findById(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);	
		
		restaurante.desvincularFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void vincularUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = this.findById(restauranteId);
		Usuario usuario = usuarioService.findById(usuarioId);	
		
		restaurante.vincularUsuario(usuario);
	}
	
	@Transactional
	public void desvincularUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = this.findById(restauranteId);
		Usuario usuario = usuarioService.findById(usuarioId);	
		
		restaurante.desvincularUsuario(usuario);
	}
	
}
