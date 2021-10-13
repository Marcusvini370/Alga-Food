package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();

	void remover(Restaurante restaurante);

	Restaurante salvar(Restaurante restaurante);

	Restaurante buscar(Long id);

}
