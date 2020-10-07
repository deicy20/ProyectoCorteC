package edu.cecar.modelo;

public class Pais {
	private String nombre;
	private String[] fechas;
	private int[] totalCasosConfirmados;
	private int[] totalNuevosCasosConfirmados;
	private int[] totalMuertes;
	private int[] totalNuevasMuertes;

	public Pais() {

	}
	public Pais(String nombre, String[] fechas, int[] totalCasosConfirmados, int[] totalNuevosCasosConfirmados,
			int[] totalMuertes, int[] totalNuevasMuertes) {
		this.nombre = nombre;
		this.fechas = fechas;
		this.totalCasosConfirmados = totalCasosConfirmados;
		this.totalNuevosCasosConfirmados = totalNuevosCasosConfirmados;
		this.totalMuertes = totalMuertes;
		this.totalNuevasMuertes = totalNuevasMuertes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String[] getFechas() {
		return fechas;
	}

	public void setFechas(String[] fechas) {
		this.fechas = fechas;
	}

	public int[] getTotalCasosConfirmados() {
		return totalCasosConfirmados;
	}

	public void setTotalCasosConfirmados(int[] totalCasosConfirmados) {
		this.totalCasosConfirmados = totalCasosConfirmados;
	}

	public int[] getTotalNuevosCasosConfirmados() {
		return totalNuevosCasosConfirmados;
	}

	public void setTotalNuevosCasosConfirmados(int[] totalNuevosCasosConfirmados) {
		this.totalNuevosCasosConfirmados = totalNuevosCasosConfirmados;
	}

	public int[] getTotalMuertes() {
		return totalMuertes;
	}

	public void setTotalMuertes(int[] totalMuertes) {
		this.totalMuertes = totalMuertes;
	}

	public int[] getTotalNuevasMuertes() {
		return totalNuevasMuertes;
	}

	public void settotalNuevasMuertes(int[] totalNuevasMuertes) {
		this.totalNuevasMuertes = totalNuevasMuertes;
	}

}
