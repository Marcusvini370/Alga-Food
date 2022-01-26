package com.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.CidadeNaoEncontradaExcpetion;
import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de cidade com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Transactional
	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstado.BuscarOuFalhar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaExcpetion(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmusoExcpetion(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade BuscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new CidadeNaoEncontradaExcpetion(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
	}

}
