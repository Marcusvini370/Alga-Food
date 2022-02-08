package com.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	@NotNull
	@FileSize(max = "13MB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;

}
