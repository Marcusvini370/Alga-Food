package com.algafood.domain.model.dto.input;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CozinhaIdinput {

    @NotNull
    private Long id;

}
