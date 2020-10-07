package edu.cecar.logica;

/**
 * Clase que principal para ejecutar le programa
 */
import java.util.Scanner;

import edu.cecar.modelo.Correo;
import edu.cecar.persistencia.AdministrarCorreo;
import edu.cecar.utilidad.CargarArchivos;
import edu.cecar.utilidad.ExcepcionValidacionOpcion;

public class Principal {
	String[] paises = {"Honduras","Colombia","Chile","Mexico","Peru","Argentina",
			"Canada","Ecuador","Panama","Guatemala"};
	
	/**
	 * Metodo que muestra el munu principal
	 */
	public void menu() {
		AdministrarCorreo adminCorreo = new AdministrarCorreo();
		Correo correo;
		ProcesarAExcel procesar = new ProcesarAExcel();
		EnviarReporte enviar = new EnviarReporte();
		Scanner tc = new Scanner(System.in);

		System.out.println("---------------------------");
		System.out.println("SCORONAVIRUS MENU");
		System.out.println("1: AGREGAR UN CORREO");
		System.out.println("2: ELIMINAR UN CORREO");
		System.out.println("3: SCRAPEAR PDFs");
		System.out.println("4: ENVIAR REPORTE A TODOS LOS CORREOS");
		System.out.println("5: SALIR");
		System.out.print("INGRESE UNA OPCION: ");

		try {
			int opcion = tc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("-----------------");
				System.out.print("Ingrese una direccion de correo: ");
				String direccion = tc.next();
				correo = new Correo(direccion);
				adminCorreo.guardarCorreo(correo);
				menu();
				break;

			case 2:
				System.out.println("-----------------");
				adminCorreo.consultarCorreos();
				System.out.print("Ingrese una ID del correo: ");
				int id = tc.nextInt();
				adminCorreo.eliminarCorreo(id);
				menu();
				break;
			case 3:
				String[] rutasCargadas = CargarArchivos.cargar().clone();
				for (String string : rutasCargadas) {
					System.out.println(string);
				}
				System.out.println("-----------------");
				System.out.println("Scrapeando PDFs...");
				for (int i = 0; i < paises.length; i++) {
					procesar.exportarAExcel(paises, rutasCargadas);
					System.out.println(paises[i]+" scrapeado");
				}
				System.out.println("PDFs Scrapeandos y reporte generado! ");
				menu();
				break;
			case 4:
				System.out.println("-----------------");
				System.out.println("Enviando correo...");
				enviar.mandarCorreo(enviar.getDestinatarios());
				System.out.println("Correos enviados!");
				menu();
				break;
			case 5:
				System.out.println("Programa finalizado!");
				System.exit(0);
				break;
			default:
				break;
			}
			throw new ExcepcionValidacionOpcion(opcion);
		}catch (ExcepcionValidacionOpcion ex) {
			System.out.println(ex);
			menu();
		} catch (Exception e) {
			System.out.println("Valor de opcion desconocido!");
			menu();
		} 
	}
	public static void main(String[] args) {
		new Principal().menu();
	}
}
