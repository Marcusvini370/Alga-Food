package com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algafood.domain.model.Restaurante;

public interface RestauranteReposiotryQueries {

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);

}