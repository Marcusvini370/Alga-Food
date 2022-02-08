package com.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	void remover (String nomeArquivo);
	
	InputStream recuperar(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFtoto) {
		this.armazenar(novaFtoto);
		
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NovaFoto{
		private String nomeArquivo;
		private InputStream inputStream; //fluxo de entrada do arquivo
	}
}
