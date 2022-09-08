package com.algafood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria extends RepresentationModel<VendaDiaria> {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

}
