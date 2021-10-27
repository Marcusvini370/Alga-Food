package com.algafood.infracstruture.repository;

import static com.algafood.infracstruture.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algafood.infracstruture.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteReposiotryQueries;
import com.algafood.domain.repository.RestauranteRepository;


@Repository
public class RestauranteRepositoryImpl implements RestauranteReposiotryQueries { 

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override 
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		
		 var criteria = builder.createQuery(Restaurante.class);
		 
		 var  root = criteria.from(Restaurante.class); //from Restaurante
		 
		 var predicates = new ArrayList<>();
		 
		 if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		 }
		
		 if (taxaFreteInicial != null) {
			 predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		 }
		 
		 if (taxaFreteFinal != null) {
			 predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		 }
		 
		 criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
			
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {


		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	
}
