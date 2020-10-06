package com.deliveryfood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.api.converter.FotoProdutoModelConverter;
import com.deliveryfood.api.model.FotoProdutoModel;
import com.deliveryfood.api.model.input.FotoProdutoInput;
import com.deliveryfood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.deliveryfood.core.storage.FotoStorageService;
import com.deliveryfood.core.storage.FotoStorageService.FotoRecuperada;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.service.CatalogoFotoProdutoService;
import com.deliveryfood.domain.service.ProdutoService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoModelConverter fotoProdutoModelConverter;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Autowired
	private CatalogoFotoProdutoService catalogoService;

	@GetMapping
	public FotoProdutoModel findDadosFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto foto = catalogoService.findDadosFoto(restauranteId, produtoId);
		return fotoProdutoModelConverter.toModel(foto);
	}

	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> findArquivoFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestHeader(name = "accept") String acceptContentTypes) throws HttpMediaTypeNotAcceptableException {

		try {

			FotoProduto fotoProduto = catalogoService.findDadosFoto(restauranteId, produtoId);
			MediaType contentTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> contentTypesAceitos = MediaType.parseMediaTypes(acceptContentTypes);

			verificaCompatibilidadeContentType(contentTypeFoto, contentTypesAceitos);

			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}

			return ResponseEntity.ok().contentType(contentTypeFoto)
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel update(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto fotoProduto = buildFotoProduto(restauranteId, produtoId, fotoProdutoInput, arquivo);

		fotoProduto = catalogoService.save(fotoProduto, arquivo.getInputStream());
		FotoProdutoModel fotoSalva = fotoProdutoModelConverter.toModel(fotoProduto);

		return fotoSalva;

	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoService.delete(restauranteId, produtoId);
	}

	private FotoProduto buildFotoProduto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput,
			MultipartFile arquivo) {

		Produto produto = produtoService.findById(restauranteId, produtoId);

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setContentType(arquivo.getContentType());
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setId(produtoId);
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		foto.setTamanho(arquivo.getSize());

		return foto;
	}

	private void verificaCompatibilidadeContentType(MediaType contentTypeFoto, List<MediaType> contentTypesAceitos)
			throws HttpMediaTypeNotAcceptableException {

		boolean compativel = contentTypesAceitos.stream()
				.anyMatch(contentType -> contentType.isCompatibleWith(contentTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(contentTypesAceitos);
		}
	}
}
