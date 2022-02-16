package com.algafood.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.net.HttpHeaders;

import lombok.experimental.UtilityClass;

@UtilityClass //classe que não pode ser extendida, tbm n pode gerar construtor
public class ResourceUriHelper {

	public static void addUriResponseHeader(Object resourceId) {
		
		// classe que ajuda criar uri usando as informações da requisição atual
		URI uri =ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") // a partir da uri adiciona id
				.buildAndExpand(resourceId) //coloca no placeholder id acima
				.toUri();
		
			HttpServletResponse response = ((ServletRequestAttributes) 
					RequestContextHolder.getRequestAttributes()).getResponse();
				
			response.setHeader(HttpHeaders.LOCATION, uri.toString());
		
	}
	
}
