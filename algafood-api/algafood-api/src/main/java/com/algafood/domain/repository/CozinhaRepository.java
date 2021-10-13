package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();

	void remover(Cozinha cozinha);

	Cozinha salvar(Cozinha cozinha);

	Cozinha buscar(Long id);

}
