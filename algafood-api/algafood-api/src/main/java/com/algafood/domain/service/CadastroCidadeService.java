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
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de estado com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
    private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	 public Cidade salvar(Cidade cidade) {
		 
         Long estadoId = cidade.getEstado().getId();
         
         Estado estado = cadastroEstado.BuscarOuFalhar(estadoId);
        		 
         cidade.setEstado(estado);
         
         return cidadeRepository.save(cidade);
     }
	 
     
     public void excluir(Long cidadeId) {
         try {
             cidadeRepository.deleteById(cidadeId);
             
         } catch (EmptyResultDataAccessException e) {
             throw new EntidadeNaoEncontradaExcpetion(
                 String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
         
         } catch (DataIntegrityViolationException e) {
             throw new EntidadeEmusoExcpetion(
                 String.format(MSG_CIDADE_EM_USO, cidadeId));
         }
     }
     
     public Cidade BuscarOuFalhar(Long cidadeId) {
 		return cidadeRepository.findById(cidadeId)
 				.orElseThrow(() -> new EntidadeNaoEncontradaExcpetion(String.format(
 						MSG_CIDADE_NAO_ENCONTRADA, cidadeId))); 
 	}

}
