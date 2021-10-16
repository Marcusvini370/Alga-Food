package com.algafood.api.model;

import java.util.List;

import com.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {
	
	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false) // desabilita o embrulho
	@NonNull // gera o construtor com cozinhas
	private List<Cozinha> cozinhas;
	
}
