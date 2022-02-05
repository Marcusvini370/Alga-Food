package com.algafood.infracstruture.service.reports;

import org.springframework.stereotype.Service;

import com.algafood.domain.filter.VendaDiariaFilter;
import com.algafood.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService{

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		return null;
	}

}
