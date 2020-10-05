package com.deliveryfood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.deliveryfood.api.exceptionhandler.model.Problem;
import com.deliveryfood.api.model.EstadoModel;
import com.deliveryfood.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    List<EstadoModel> findAll();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModel findById(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    ResponseEntity<EstadoModel> save(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Estado atualizado"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    ResponseEntity<EstadoModel> update(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,
            
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Estado excluído"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void delete(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);
}