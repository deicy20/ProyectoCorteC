package edu.cecar.logica;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTSerTx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTCatAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScaling;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLegend;
import org.openxmlformats.schemas.drawingml.x2006.chart.STAxPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.STBarDir;
import org.openxmlformats.schemas.drawingml.x2006.chart.STOrientation;
import org.openxmlformats.schemas.drawingml.x2006.chart.STLegendPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;

import edu.cecar.modelo.Pais;

public class ProcesarAExcel {
	ScrapingPDF scrapear = new ScrapingPDF();
	
	public void exportarAExcel(String[] paises, String[]rutas) throws Exception{
		Workbook wb = new XSSFWorkbook();
		
		for (int i = 0; i < paises.length; i++) {
			Sheet sheet = wb.createSheet(paises[i]);
			Row row;
	        Cell cell;
	        row = sheet.createRow(0);
	        row.createCell(0);
	        row.createCell(1).setCellValue("TOTAL CONFIRMED CASE");
	        row.createCell(2).setCellValue("NEW CASES");
	        row.createCell(3).setCellValue("TOTAL DEATHS");
	        row.createCell(4).setCellValue("TOTAL NEW DEATHS");
	        
	        Pais pais;
	        pais = scrapear.scraping(rutas, paises[i]);
	        int cont = 0;
	        
	        
	        for (int r = 1; r < 5; r++) {
	            row = sheet.createRow(r);
	            cell = row.createCell(0);
	            cell.setCellValue(pais.getFechas()[r-1]);
	            cell = row.createCell(1);
	            cell.setCellValue(pais.getTotalCasosConfirmados()[r-1]);
	            cell = row.createCell(2);
	            cell.setCellValue(pais.getTotalNuevosCasosConfirmados()[r-1]);
	            cell = row.createCell(3);
	            cell.setCellValue(pais.getTotalMuertes()[r-1]);
	            cell = row.createCell(4);
	            cell.setCellValue(pais.getTotalNuevasMuertes()[r-1]); 

	        }
	        XSSFDrawing drawing = (XSSFDrawing)sheet.createDrawingPatriarch();
	        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 8, 20);

	        XSSFChart chart = drawing.createChart(anchor);

	        CTChart ctChart = ((XSSFChart)chart).getCTChart();
	        CTPlotArea ctPlotArea = ctChart.getPlotArea();
	        CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
	        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
	        ctBoolean.setVal(true);
	        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
	        
	        for (int r = 2; r < 7; r++) {
	            CTBarSer ctBarSer = ctBarChart.addNewSer();
	            CTSerTx ctSerTx = ctBarSer.addNewTx();
	            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
	            ctStrRef.setF(paises[i]+"!$A$" + r);
	            ctBarSer.addNewIdx().setVal(r-2);  
	            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
	            ctStrRef = cttAxDataSource.addNewStrRef();
	            ctStrRef.setF(paises[i]+"!$B$1:$E$1"); 
	            CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
	            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
	            ctNumRef.setF(paises[i]+"!$B$" + r + ":$E$" + r);

	            
	            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] {0,0,0});   
	         } 
	        ctBarChart.addNewAxId().setVal(100);
	        ctBarChart.addNewAxId().setVal(1000);

	   
	        CTCatAx ctCatAx = ctPlotArea.addNewCatAx(); 
	        ctCatAx.addNewAxId().setVal(100); 
	        CTScaling ctScaling = ctCatAx.addNewScaling();
	        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
	        ctCatAx.addNewDelete().setVal(false);
	        ctCatAx.addNewAxPos().setVal(STAxPos.B);
	        ctCatAx.addNewCrossAx().setVal(1000); 
	        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

	       
	        CTValAx ctValAx = ctPlotArea.addNewValAx(); 
	        ctValAx.addNewAxId().setVal(1000);
	        ctScaling = ctValAx.addNewScaling();
	        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
	        ctValAx.addNewDelete().setVal(false);
	        ctValAx.addNewAxPos().setVal(STAxPos.L);
	        ctValAx.addNewCrossAx().setVal(100);
	        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

	        
	        CTLegend ctLegend = ctChart.addNewLegend();
	        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
	        ctLegend.addNewOverlay().setVal(false);
	        
		}
		FileOutputStream fileOut = new FileOutputStream("src/PDFsProcesados/reporte.xlsx");
        wb.write(fileOut);
        fileOut.close();
	}
	
       
}
