/**
 * Esta clase contine metodo para cargar los pdf
 */
package edu.cecar.utilidad;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CargarArchivos {

	/**
	 * metodo para cargar la ruta de los archivos
	 * @return
	 */
	public static String[] cargar() {
		String[] rutas = null;
		JFileChooser chooser = new JFileChooser();
		FileFilter filtro = new FileNameExtensionFilter("Archivos (.pdf)", "pdf");
		chooser.setFileFilter(filtro);
		chooser.setMultiSelectionEnabled(true);
		int valor = chooser.showOpenDialog(chooser);
		if(valor == JFileChooser.APPROVE_OPTION) {
			File[] archivos = chooser.getSelectedFiles();
			rutas = new String[archivos.length];
			for (int i = 0; i < archivos.length; i++) {
				rutas[i] = archivos[i].getAbsolutePath();
			}
			
		}
		return rutas;
	}
}
