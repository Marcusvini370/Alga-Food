package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();

	void remover(Long cozinhaId);

	Cozinha salvar(Cozinha cozinha);

	Cozinha buscar(Long id);

}
