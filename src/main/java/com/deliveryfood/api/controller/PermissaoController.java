package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.assembler.PermissaoAssembler;
import com.deliveryfood.api.model.PermissaoModel;
import com.deliveryfood.api.openapi.controller.PermissaoControllerOpenApi;
import com.deliveryfood.domain.model.Permissao;
import com.deliveryfood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoAssembler permissaoAssembler;
    
    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> findAll() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoAssembler.toCollectionModel(todasPermissoes);
    }   
}        