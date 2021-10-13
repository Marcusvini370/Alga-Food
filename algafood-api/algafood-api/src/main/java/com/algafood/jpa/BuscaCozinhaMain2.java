package com.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algafood.AlgafoodApiApplication;
import com.algafood.domain.model.Cozinha;

public class BuscaCozinhaMain2 {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
			CadastroCozinha cadastroCozinha =  applicationContext.getBean(CadastroCozinha.class);
			
			Cozinha cozinha = cadastroCozinha.buscar(1L);
			
			System.out.println(cozinha.getNome());
				
		
	}
	
}
