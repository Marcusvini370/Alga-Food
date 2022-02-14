package com.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
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
          .build() //retorna o docket;
          .useDefaultResponseMessages(false) //desativa os código de erro gerado auto
          .globalResponses(HttpMethod.GET, globalGetResponseMessages()) //conf cod de status padrao pro mét get
          .apiInfo(apiInfo()) //traz as configurações do método para a documentação
          .tags(new Tag("Cidades", "Gerencia as cidades"));
  }
  
  

		private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())) // exemplo tentar acessar por xml, sendo que é json
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
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