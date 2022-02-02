package com.algafood.domain.service;

import java.util.List;

import com.algafood.domain.filter.VendaDiariaFilter;
import com.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,String timeOffset);


}
