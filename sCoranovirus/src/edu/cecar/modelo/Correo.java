/**
 * Clase para modelar objetos de tipo correo
 */
package edu.cecar.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Correo {
	private String direccion;

	/**
	 * metodos contrustores
	 */
	public Correo() {
		
	}
	
	public Correo(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo para validar una direccion de correo valida
	 * @param correo
	 * @return valido o invalido
	 */
	public boolean validarCorreo(String correo) {
		boolean valido = false;
		 String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
			      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
			      Pattern pattern = Pattern.compile(emailPattern);
			      if (correo != null) {
			        Matcher matcher = pattern.matcher(correo);
			        if (matcher.matches()) {
			         valido = true;
			        }
			  }
				return valido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
