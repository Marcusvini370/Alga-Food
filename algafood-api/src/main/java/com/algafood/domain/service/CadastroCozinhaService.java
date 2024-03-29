package com.algafood.domain.service;

import com.algafood.domain.exception.CozinhaNaoEncontradaExcpetion;
import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    private static final String MSG_COZINHA_NAO_ENCOTNADA = "Não existe um cadastro de cozinha com código %d";
    @Autowired
    CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaExcpetion(String.format(MSG_COZINHA_NAO_ENCOTNADA, cozinhaId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmusoExcpetion(String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha BuscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId).orElseThrow(
                () -> new CozinhaNaoEncontradaExcpetion(String.format(MSG_COZINHA_NAO_ENCOTNADA, cozinhaId)));
    }

}
