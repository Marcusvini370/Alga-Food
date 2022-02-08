package com.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.api.dto.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			FotoProdutoInput fotoProdutoInput) {

		var nomeArquivo = UUID.randomUUID().toString() // transferir o arquivo que fez upload para esse nome
				+ "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

		var arquivoFoto = Path.of("/Users/Vincius/Desktop/upload", nomeArquivo); // salva nesse caminho

		try {
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
