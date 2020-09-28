package com.deliveryfood.domain.listerner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.core.service.email.EmailService;
import com.deliveryfood.core.service.email.EmailService.Mensagem;
import com.deliveryfood.domain.event.PedidoCanceladoEvent;
import com.deliveryfood.domain.model.Pedido;

@Component
public class NotificacaoClientePedidoCancelado {

	@Autowired
	private EmailService emailService;
	
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado!")
				.corpo("pedido-cancelado.html")
				.campo("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		emailService.enviar(mensagem);
		
		
	}
}
