package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.exception.EstadoNaoEncontradoExcpetion;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }
    
    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoExcpetion(
                String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmusoExcpetion(
                String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
    
    public Estado BuscarOuFalhar(Long EstadoId) {
		return estadoRepository.findById(EstadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoExcpetion(String.format(
						MSG_ESTADO_NAO_ENCONTRADO, EstadoId))); 
	}
	

}
