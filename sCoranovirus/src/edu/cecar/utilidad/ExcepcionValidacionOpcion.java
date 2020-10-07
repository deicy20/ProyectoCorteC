package edu.cecar.utilidad;

public class ExcepcionValidacionOpcion  extends Exception{
	private int valor;
	
	public ExcepcionValidacionOpcion(int valor) {
		this.valor = valor;
	}
	@Override
	public String getMessage() {
		String mensaje = "";
		if(valor>5 || valor<0 ) {
			mensaje = "Opcion invalida";
		}

		return mensaje;
	}
}
