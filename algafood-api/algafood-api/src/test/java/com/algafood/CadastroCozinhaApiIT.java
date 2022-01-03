package com.algafood;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaApiIT {

	@LocalServerPort
	private int port;
	
	@BeforeEach // vai executar toda vez antes de passar por um teste
	public void setUp() {
		//mostra o log quando falha o teste
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
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
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // retorna o log no console quando falhar
		
		given() //dado que
			.accept(ContentType.JSON)  //aceita retorno em json
		.when() // quando
			.get()
		.then()
		.body("", Matchers.hasSize(4)) // 4cozinhas
		.body("nome", Matchers.hasItems("Indiana", "Tailandesa")); // cozinhas especifícas
	}

			
}
