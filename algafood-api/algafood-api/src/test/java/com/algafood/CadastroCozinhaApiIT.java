package com.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") 
// vai continuar usando o application.properties, mas vai subistituir as usadas no test.properties
class CadastroCozinhaApiIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach // vai executar toda vez antes de passar por um teste
	public void setUp() {
		//mostra o log quando falha o teste
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		databaseCleaner.clearTables(); //limpa os dados de todas tabelas do banco
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given() //dado que
			.accept(ContentType.JSON)  //aceita retorno em json
		.when() // quando
			.get()
		.then()
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterCozinhas_QuandoConsultarCozinhas() {		
		given() //dado que
			.accept(ContentType.JSON)  //aceita retorno em json
		.when() // quando
			.get()
		.then()
		.body("", Matchers.hasSize(2)); // número de cozinhas para passar pelo teste
		//.body("nome", Matchers.hasItems("Indiana", "Tailandesa")); // cozinhas especifícas
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarCozinha() {
		given()
			.body("{\"nome\": \"Chinesa\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given() 
			.pathParam("cozinhaId", 2) // indicar o parametro e o id
			.accept(ContentType.JSON) 
		.when() 
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("Americana"));
		
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given() 
			.pathParam("cozinhaId", 100) // indicar o parametro e o id
			.accept(ContentType.JSON) 
		.when() 
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}

			
}
