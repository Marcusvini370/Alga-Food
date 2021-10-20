package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }
    
    public void excluir(Long estadoId) {
        try {
            estadoRepository.remover(estadoId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaExcpetion(
                String.format("Não existe um cadastro de estado com código %d", estadoId));
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmusoExcpetion(
                String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
        }
    }
	

}
