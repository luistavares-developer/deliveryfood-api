package com.deliveryfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.converter.PedidoConverter;
import com.deliveryfood.api.converter.PedidoResumoModelConverter;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;
import com.deliveryfood.domain.repository.filter.PedidoFilter;
import com.deliveryfood.domain.repository.spec.PedidoSpecs;
import com.deliveryfood.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private PedidoConverter pedidoConverter;
    
    @Autowired
    private PedidoResumoModelConverter pedidoResumoModelConverter;
    
    @GetMapping
    public Page<PedidoResumoModel> findByFilter(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.filter(filter), pageable);
        
        List<PedidoResumoModel> pedidos = pedidoResumoModelConverter.toCollectionModel(pedidosPage.getContent());
        
        Page<PedidoResumoModel> pedidosResumoPage = new PageImpl<>(pedidos, pageable, pedidosPage.getTotalElements());
        
        return pedidosResumoPage;
    }
    
    @GetMapping("/{codigoPedido}")
    public PedidoModel findById(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.findByCodigo(codigoPedido);
        
        return pedidoConverter.toModel(pedido);
    }  
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel save(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoConverter.toDomain(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.save(novoPedido);

            return pedidoConverter.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    @PutMapping("/{codigoPedido}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
    	pedidoService.confirmar(codigoPedido);
    }
    
    @PutMapping("/{codigoPedido}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
    	pedidoService.entregar(codigoPedido);
    }
    
    @PutMapping("/{codigoPedido}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
    	pedidoService.cancelar(codigoPedido);
    }
}           