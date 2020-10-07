package edu.cecar.logica;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import edu.cecar.modelo.Pais;

public class ScrapingPDF {

	/**
	 * Obtiene la fecha de un odf formateada
	 * @param ruta del archivo
	 * @return
	 * @throws IOException 
	 */
	public String getFechaPDF(String ruta, PDDocument doc) throws IOException {
		String fecha ="";
		doc = PDDocument.load(new File(ruta));
		PDFTextStripper pts = new PDFTextStripper();
		pts.setStartPage(doc.getNumberOfPages());
		String texto = pts.getText(doc);
		String[] lines = texto.split("\n");
		for (int i = 0; i < lines.length; i++) {

			if (lines[i].contains("Update")) {
				String encontrada=  (lines[i]);
				fecha = encontrada.substring(9, encontrada.indexOf(','));
				break;
			}
		}
		return fecha;
	}
	/**
	 * metodo convierte los numeros de los pdf en valores enteros
	 * @param numero
	 * @return
	 */
	public int formatearNumeros(String numero) {
		int numeroFormateado = 0;
		if (numero.contains("Community")) {
			String aux = numero.substring(0,numero.indexOf('C'));
			numeroFormateado = Integer.parseInt(aux.replaceAll(" ", ""));
		}else {
			numeroFormateado = Integer.parseInt(numero.replaceAll(" ", ""));
		}

		return numeroFormateado;
	}
	/**
	 * metodos que devuelte un objeto de tipó Pais con todos los datos de os PDFs
	 * @param ruta
	 * @param pdfs
	 * @return
	 * @throws IOException
	 */
	public Pais scraping(String[] rutas, String pais) throws IOException {
		String nombre = pais;
		String[] fechas = new String[4];
		int[] totalCasosConfirmados= new int[4];
		int[] totalNuevosCasosConfirmados= new int[4];
		int[] totalMuertes= new int[4];
		int[] totalNuevasMuertes= new int[4];

		for (int r = 0; r < rutas.length; r++) {
			//System.out.println(r);
			PDDocument doc = PDDocument.load(new File(rutas[r]));
			//System.out.println("Cargado pdf "+r);
			PDFTextStripper pts = new PDFTextStripper();
			fechas[r] = getFechaPDF(rutas[r], doc);
			//System.out.println(fechas[r]);
			pts.setStartPage(6);
			pts.setEndPage(9);
			String texto = pts.getText(doc);
			String[] lineas = texto.split("\n");
			for (int i = 0; i < lineas.length; i++) {
				if (lineas[i].contains(pais)) {
					String[] datos = lineas[i].split("  |   ");
					for (int k = 0; k < datos.length; k++) {
						totalCasosConfirmados[r] = formatearNumeros(datos[1]);
						totalNuevosCasosConfirmados[r] = formatearNumeros(datos[2]);
						totalMuertes[r] = formatearNumeros(datos[3]);
						totalNuevasMuertes[r] = formatearNumeros(datos[4]);
					}
					break;
				}
			}
			doc.close();
		}
		Pais Objpais = new Pais(nombre, fechas, totalCasosConfirmados,
				totalNuevosCasosConfirmados, totalMuertes, totalNuevasMuertes);

		return Objpais;
	}
	
}
