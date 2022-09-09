package com.algafood.core.openapi;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.*;
import com.algafood.api.v1.openapi.model.*;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocketv1() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30) //configuração da documentação
                .groupName("V1")
                .select() //retornando builder para selecionar os endpoints para sempre expostos
                .apis(RequestHandlerSelectors.basePackage("com.algafood.api")) // seleciona pasta dos controladores a documentar
                .paths(PathSelectors.ant("/v1/**"))
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
                .ignoredParameterTypes(ServletWebRequest.class, // ignora parametros, util pra tirar os n usados
                        URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // organiza a doc de paginação p subst
                .directModelSubstitute(Links.class, LinksModelOpenApi.class) //organizar links configurados do hateoas
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaDTO.class), CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoDTO.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
                        CidadesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
                        UsuariosModelOpenApi.class))
                .apiInfo(apiInfoV1()) //traz as configurações do método para a documentação
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões"));
    }

    @Bean
    public Docket apiDocketV2() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30) //configuração da documentação
                .groupName("V2")
                .select() //retornando builder para selecionar os endpoints para sempre expostos
                .apis(RequestHandlerSelectors.basePackage("com.algafood.api")) // seleciona pasta dos controladores a documentar
                .paths(PathSelectors.ant("/v2/**"))
                // .paths(PathSelectors.ant("/restaurantes/*")) controlador específico
                .build() //retorna o docket;
                .useDefaultResponseMessages(false) //desativa os código de erro gerado auto
                .globalResponses(HttpMethod.GET, globalGetResponseMessages()) //conf cod de status padrao pro mét get
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class)) // modelo extra pra adicionar na doc.
                .ignoredParameterTypes(ServletWebRequest.class, // ignora parametros, util pra tirar os n usados
                        URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // organiza a doc de paginação p subst
                .directModelSubstitute(Links.class, LinksModelOpenApi.class) //organizar links configurados do hateoas
                .apiInfo(apiInfoV2()); //traz as configurações do método para a documentação

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
                        .representation(MediaType.APPLICATION_JSON)
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
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }


    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Algaworks", "https://github.com/Marcusvini370/Alga-Food", "marcusvini370@gmail.com"))
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(new Contact("Algaworks", "https://github.com/Marcusvini370/Alga-Food", "marcusvini370@gmail.com"))
                .build();
    }

}