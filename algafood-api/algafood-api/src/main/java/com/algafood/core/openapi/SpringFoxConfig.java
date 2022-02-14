package com.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.OAS_30) //configuração da documentação
        .select() //retornando builder para selecionar os endpoints para sempre expostos
          .apis(RequestHandlerSelectors.basePackage("com.algafood.api")) // seleciona pasta dos controladores a documentar
          .paths(PathSelectors.any())
         // .paths(PathSelectors.ant("/restaurantes/*")) controlador específico
          .build(); //retorna o docket;
  }
  
}