package com.algafood.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO {

    //@JsonView(value = {RestauranteView.resumo.class, RestauranteView.apenasNome.class})
    private Long id;


   // @JsonView(value = {RestauranteView.resumo.class, RestauranteView.apenasNome.class})
    private String nome;

   // @JsonView(RestauranteView.resumo.class)
    private BigDecimal taxaFrete;

   // @JsonView(RestauranteView.resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;


}
