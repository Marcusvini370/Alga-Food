package com.algafood.domain.service;

import com.algafood.domain.filter.VendaDiariaFilter;


public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro,String timeOffset);
	
}
