package com.deliveryfood.api.controller;

import static com.deliveryfood.api.assembler.hateaos.LinkAssembler.linkToEstatisticasVendasDiarias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.openapi.controller.EstatisticasControllerOpenApi;
import com.deliveryfood.core.report.PdfVendaReportService;
import com.deliveryfood.domain.model.VendaDiaria;
import com.deliveryfood.domain.repository.VendaDiariaRepository;
import com.deliveryfood.domain.repository.filter.VendaDiariaFilter;

@RestController
@RequestMapping(path = "/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController implements EstatisticasControllerOpenApi {

	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
	
	@Autowired
	private VendaDiariaRepository vendaDiariaRepository;
	
	@Autowired
	private PdfVendaReportService pdfVendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaDiariaRepository.buscaVendasDiaria(filtro, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = pdfVendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	}   
	
}

