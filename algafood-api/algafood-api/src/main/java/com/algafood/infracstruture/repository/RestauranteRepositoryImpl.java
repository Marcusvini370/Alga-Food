package com.algafood.infracstruture.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {
	

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> listar(){
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
					
	}

	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}
	

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurane) {
		return manager.merge(restaurane);
		}
	
	

	@Transactional
	@Override
	public void remover(Restaurante restaurane) {
		restaurane = buscar(restaurane.getId()); 
		manager.remove(restaurane); 
	}

}
