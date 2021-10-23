package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
    private EstadoRepository estadoRepository;
	
	 public Cidade salvar(Cidade cidade) {
         Long estadoId = cidade.getEstado().getId();
         Estado estado = estadoRepository.findById(estadoId)
        		 .orElseThrow(() -> new EntidadeNaoEncontradaExcpetion(
        		     String.format("Não existe cadastro de estado com código %d", estadoId)));
         
         
         
         cidade.setEstado(estado);                                                                                                                                                                                                                                                                                                                                                                                                         
         
         return cidadeRepository.save(cidade);
     }
     
     public void excluir(Long cidadeId) {
         try {
             cidadeRepository.deleteById(cidadeId);
             
         } catch (EmptyResultDataAccessException e) {
             throw new EntidadeNaoEncontradaExcpetion(
                 String.format("Não existe um cadastro de cidade com código %d", cidadeId));
         
         } catch (DataIntegrityViolationException e) {
             throw new EntidadeEmusoExcpetion(
                 String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
         }
     }

}
