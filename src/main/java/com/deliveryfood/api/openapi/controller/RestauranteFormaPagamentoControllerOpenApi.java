package com.deliveryfood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.deliveryfood.api.exceptionhandler.model.Problem;
import com.deliveryfood.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {
    
	@ApiOperation("Lista as formas de pagamento associadas a restaurante")
	@ApiResponses({
	    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	CollectionModel<FormaPagamentoModel> findAll(
	        @ApiParam(value = "ID do restaurante", example = "1", required = true)
	        Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)
    })
    ResponseEntity<Void> desvincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)
    })
    ResponseEntity<Void> vincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);
}