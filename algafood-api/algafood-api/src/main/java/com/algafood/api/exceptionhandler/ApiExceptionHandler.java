package com.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.exception.NegocioException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(EntidadeEmusoExcpetion.class)
	public ResponseEntity<?> tratarEntidadeEmusoExcpetion(
			EntidadeEmusoExcpetion ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
		
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaExcpetion.class)
	public ResponseEntity<?> tratarEntidadeNaoEcnotradoException(
			EntidadeNaoEncontradaExcpetion ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(
			EntidadeNaoEncontradaExcpetion ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
		
			body = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(status.getReasonPhrase()).build();
			
		}else if (body instanceof String) {
			
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem((String) body).build();
			
		}
		
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	

}
