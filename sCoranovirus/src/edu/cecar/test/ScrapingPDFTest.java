package edu.cecar.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.cecar.logica.ScrapingPDF;
import edu.cecar.modelo.Pais;

public class ScrapingPDFTest {

	@Test
	public void testScraping() {
		ScrapingPDF scraping = new ScrapingPDF();
		//para el PDF 2020/08/12
		String[] rutas = {"src/PDFs/20200812-covid-19-sitrep-205.pdf"};
		try {
			Pais ConfirmadosEsperados = scraping.scraping(rutas, "Colombia");
			int resultado =  ConfirmadosEsperados.getTotalCasosConfirmados()[0];
			int esperado = 397623; //casos en el documento
			assertEquals(esperado, resultado);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
