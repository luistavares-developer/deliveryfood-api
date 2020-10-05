package com.deliveryfood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.api.model.annotation.ContentType;
import com.deliveryfood.api.model.annotation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "500MB")
	@ContentType(allow = {MediaType.APPLICATION_PDF_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
