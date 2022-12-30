package com.algafood.api.v1.controller;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.repository.RestauranteRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Operation(hidden = true)
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }

    @Operation(hidden = true)
    @GetMapping("/cozinhas/exists")
    public Boolean cozinhasExists(String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restarantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restarantesPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {

        return restauranteRepository.findComFreteGratis(nome);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restarantesPorNome(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> RestaurantePrimeiroPorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/dois-por-nome")
    public List<Restaurante> restarantesTop2PorNomes(String nome) {
        return restauranteRepository.findTop2BynomeContaining(nome);
    }

    @Operation(hidden = true)
    @GetMapping("/restaurantes/count-por-cozinha")
    public int restarantesCountCozinha(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

}
