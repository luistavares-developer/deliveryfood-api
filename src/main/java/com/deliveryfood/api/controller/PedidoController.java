package com.deliveryfood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.PedidoAssembler;
import com.deliveryfood.api.assembler.PedidoResumoModelAssembler;
import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.api.openapi.controller.PedidoControllerOpenApi;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;
import com.deliveryfood.domain.repository.filter.PedidoFilter;
import com.deliveryfood.domain.repository.spec.PedidoSpecs;
import com.deliveryfood.domain.service.PedidoService;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private PedidoAssembler pedidoAssembler;
    
    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedModelAssembler;
    
    @GetMapping
    public PagedModel<PedidoResumoModel> findByFilter(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.filter(filter), pageable);
        
        PagedModel<PedidoResumoModel> pedidoResumoPagedModel = pagedModelAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
        
        return pedidoResumoPagedModel;
    }
    
    @GetMapping("/{codigoPedido}")
    public PedidoModel findById(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.findByCodigo(codigoPedido);
        
        return pedidoAssembler.toModel(pedido);
    }  
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel save(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoAssembler.toDomain(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.save(novoPedido);

            return pedidoAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    @PutMapping("/{codigoPedido}/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
    	pedidoService.confirmar(codigoPedido);
    	
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{codigoPedido}/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
    	pedidoService.entregar(codigoPedido);
    	
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{codigoPedido}/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
    	pedidoService.cancelar(codigoPedido);

    	return ResponseEntity.noContent().build();
    }
    
}           