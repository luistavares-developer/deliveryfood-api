package com.deliveryfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.PedidoNaoEncontradoException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;

	public Pedido findByCodigo(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido save(Pedido pedido) {
		validaMapeiaPedido(pedido);
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = this.findByCodigo(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);

	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = this.findByCodigo(codigoPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = this.findByCodigo(codigoPedido);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}

	private void validaMapeiaPedido(Pedido pedido) {
		Cidade cidade = cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = usuarioService.findById(pedido.getCliente().getId());
		Restaurante restaurante = restauranteService.findById(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.findById(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		validaVinculoFormaPagamentoRestaurante(pedido, restaurante);
		validaMapeiaItens(pedido);
	}
	
	private void validaVinculoFormaPagamentoRestaurante(Pedido pedido, Restaurante restaurante) {

		if (restaurante.naoAceitaFormaPagamento(pedido.getFormaPagamento())) {
			throw new NegocioException(String.format("O restaurante id %d não aceita a forma de pagamento id %d",
					restaurante.getId(), pedido.getFormaPagamento().getId()));
		}
	}
	
	private void validaMapeiaItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = produtoService.findById(pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}	
}