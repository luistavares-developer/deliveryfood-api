package com.deliveryfood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.deliveryfood.api.exceptionhandler.model.Problem;
import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	List<CidadeModel> findAll();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel findById(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true)
			Long cidadeId);
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada"),
	})
	ResponseEntity<CidadeModel> save(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
			CidadeInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	ResponseEntity<CidadeModel> update(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
			Long cidadeId,
			
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void delete(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true)
			Long cidadeId);
	
}