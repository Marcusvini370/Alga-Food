package com.algafood.core.openapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.algafood.api.dto.CozinhaDTO;
import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algafood.api.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket apiDocket() {
	  
	  var typeResolver = new TypeResolver();
	  
    return new Docket(DocumentationType.OAS_30) //configuração da documentação
        .select() //retornando builder para selecionar os endpoints para sempre expostos
          .apis(RequestHandlerSelectors.basePackage("com.algafood.api")) // seleciona pasta dos controladores a documentar
          .paths(PathSelectors.any())
         // .paths(PathSelectors.ant("/restaurantes/*")) controlador específico
          .build() //retorna o docket;
          .useDefaultResponseMessages(false) //desativa os código de erro gerado auto
          .globalResponses(HttpMethod.GET, globalGetResponseMessages()) //conf cod de status padrao pro mét get
          .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
          .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
          .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
          //  .globalRequestParameters(Collections.singletonList(
          //        new RequestParameterBuilder()
          //                .name("campos")
          //                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
          //                .in(ParameterType.QUERY)
          //                .required(false)
          //                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
          //                .build())
          // )
          .additionalModels(typeResolver.resolve(Problem.class)) // modelo extra pra adicionar na doc.
          .ignoredParameterTypes(ServletWebRequest.class) // ignora parametros, util pra tirar os n usados
          .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // organiza a doc de paginação p subst
          .alternateTypeRules(AlternateTypeRules.newRule(
        		  typeResolver.resolve(Page.class, CozinhaDTO.class), CozinhasModelOpenApi.class))
          .apiInfo(apiInfo()) //traz as configurações do método para a documentação
          .tags(new Tag("Cidades", "Gerencia as cidades"),
          new Tag("Grupos", "Gerencia os grupos de usuários"),
          new Tag("Cozinhas", "Gerencia as cozinhas"),
          new Tag("Formas de pagamento", "Gerencia as formas de pagamento"));
  }
  
  /* O método responseModel não existe na classe ResponseBuilder do SpringFox 3,
   *  assim como o ModelRef que foi depreciado. Vamos utilizar os métodos
   *   representation e apply para realizar a mesma configuração. */
  
  //método que ira gerar a referência para classe Problem:
  private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.algafood.api.exceptionhandler")))));
	}
  
  private List<Response> globalGetResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno do Servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                    .description("Recurso não possui representação que pode ser aceita pelo consumidor")
	                    .build()
	    );
	}

	private List<Response> globalPostPutResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                    .description("Requisição inválida (erro do cliente)")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno no servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                    .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
	                    .description("Requisição recusada porque o corpo está em um formato não suportado")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build()
	    );
	}

	private List<Response> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                    .description("Requisição inválida (erro do cliente)")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno no servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build()
	    );
	}
  
		@Bean
		public JacksonModuleRegistrar springFoxJacksonConfig() {
			return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
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