package com.deliveryfood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.deliveryfood.api.assembler.FormaPagamentoAssembler;
import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.model.input.FormaPagamentoInput;
import com.deliveryfood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.repository.FormaPagamentoRepository;
import com.deliveryfood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoAssembler formaPagamentoConverter;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> findAll(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = gerarEtag();
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		List<FormaPagamentoModel> formasPagamentosModel = formaPagamentoConverter
				.toCollectionModel(formaPagamentoService.findAll());

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formasPagamentosModel);
	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> findById(@PathVariable Long formaPagamentoId) {

		FormaPagamentoModel formaPagamentoModel = formaPagamentoConverter
				.toModel(formaPagamentoService.findById(formaPagamentoId));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoModel);
	}

	@PostMapping
	public ResponseEntity<FormaPagamentoModel> save(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamento = formaPagamentoConverter.toDomain(formaPagamentoInput);
		FormaPagamentoModel novaFormaPagamento = formaPagamentoConverter
				.toModel(formaPagamentoService.save(formaPagamento));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaFormaPagamento);
	}

	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> update(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamentoAtual = formaPagamentoService.findById(formaPagamentoId);
		formaPagamentoConverter.copyPropetiesToDomain(formaPagamentoInput, formaPagamentoAtual);
		FormaPagamentoModel formaPagamentoAtualizado = formaPagamentoConverter
				.toModel(formaPagamentoService.save(formaPagamentoAtual));

		return ResponseEntity.ok(formaPagamentoAtualizado);
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.delete(formaPagamentoId);
	}
	
	private String gerarEtag() {
		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		return eTag;
	}
}
