package com.algafood.domain.repository;

import com.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository
        extends JpaRepository<Restaurante, Long>, RestauranteReposiotryQueries, JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join r.cozinha")
    List<Restaurante> findAll();

    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    // @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    // List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long
    // cozinha);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2BynomeContaining(String nome);

    int countByCozinhaId(Long cozinha);

    @Query("select case when count(1) > 0 then true else false end"
            + "	from Restaurante rest"
            + "	join rest.responsaveis resp"
            + "	where rest.id = :restauranteId"
            + "	and resp.id = :usuarioId")
    boolean existsResponsavel(Long restauranteId, Long usuarioId);


}
