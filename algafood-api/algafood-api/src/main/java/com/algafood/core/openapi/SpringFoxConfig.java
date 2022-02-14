package com.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Tag;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.OAS_30) //configuração da documentação
        .select() //retornando builder para selecionar os endpoints para sempre expostos
          .apis(RequestHandlerSelectors.basePackage("com.algafood.api")) // seleciona pasta dos controladores a documentar
          .paths(PathSelectors.any())
         // .paths(PathSelectors.ant("/restaurantes/*")) controlador específico
          .build() //retorna o docket;
          .apiInfo(apiInfo()) //traz as configurações do método para a documentação
          .tags(new Tag("Cidades", "Gerencia as cidades"));
  }
  
  
  public ApiInfo apiInfo() {
	  return new ApiInfoBuilder()
			  .title("Algafood API")
			  .description("API aberta para clientes e restaurantes")
			  .version("1")
			  .contact(new Contact("Algaworks", "https://github.com/Marcusvini370/Alga-Food", "marcusvini370@gmail.com"))
			  .build();
  }
  
}