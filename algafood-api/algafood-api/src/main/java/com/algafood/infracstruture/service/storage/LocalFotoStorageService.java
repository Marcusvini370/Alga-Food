package com.algafood.infracstruture.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algafood.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService{
	
	@Value("${algafood.storage.local.deretorio-fotos}")
	private Path diretorioFotos;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo.", e);
		}
		
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}
	
}