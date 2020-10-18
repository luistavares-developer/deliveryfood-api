package com.deliveryfood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.deliveryfood.api.exceptionhandler.model.Problem;
import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoModel> findAll();
    
    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado")
    })
    GrupoModel findById(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
    
    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    ResponseEntity<GrupoModel> save(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
            GrupoInput grupoInput);
    
    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado")
    })
    ResponseEntity<GrupoModel> update(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoInput grupoInput);
    
    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado")
    })
    void delete(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
}
