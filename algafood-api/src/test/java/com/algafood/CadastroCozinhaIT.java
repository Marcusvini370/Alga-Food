package com.algafood;


import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.algafood.domain.exception.CozinhaNaoEncontradaExcpetion;
import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest //funcionalidades do springboot nos testes
class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void testarCadastroCozinhaComSucesso() {
        //cenário

        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void testarCadastroCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinha.salvar(novaCozinha);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test()
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {

        EntidadeEmusoExcpetion erroEsperado =
                Assertions.assertThrows(EntidadeEmusoExcpetion.class, () -> {
                    cadastroCozinha.excluir(1L);
                });
        assertThat(erroEsperado).isNotNull();

    }

    @Test()
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {

        CozinhaNaoEncontradaExcpetion erroEsperado =
                Assertions.assertThrows(CozinhaNaoEncontradaExcpetion.class, () -> {
                    cadastroCozinha.excluir(100L);
                });
        assertThat(erroEsperado).isNotNull();

    }

}
