/**
 * Clase para administrar correos
 */
package edu.cecar.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.cecar.modelo.Correo;

public class AdministrarCorreo {
	Connection con = new Conexion().getConnection();
	Correo correo = new Correo();

	/**
	 * Metodo para almacenar un correo en la base de datos
	 * @param correo
	 */
	public void guardarCorreo(Correo correo) {
		if(correo.validarCorreo(correo.getDireccion())) {
			try {
				PreparedStatement consulta;
				consulta = con.prepareStatement("INSERT INTO Direcciones(correo) VALUES (?);");
				consulta.setString(1, correo.getDireccion());
				consulta.executeUpdate();
				System.out.println("Correo guardado!");
			} catch (Exception e) {
				System.out.println("Algo salio mal al insertar en la base de datos!");
			}
		}else {
			System.out.println("El correo "+correo.getDireccion()+" no es valido!");
		}

	}
	/**
	 * Metodo que permite elimnar una direccion de correo por ID
	 * @param id
	 */
	public void eliminarCorreo(int id) {
		System.out.println(id);
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("DELETE FROM Direcciones WHERE idCorreo = "+id);
			consulta.executeUpdate();
			System.out.println("Correo eliminado!");
		} catch (Exception e) {
			System.out.println("Error al Eliminar correo ");
		}
	}
	/**
	 * Metodo que permite listar todos los correos existentes en la base de datos
	 */
	public void consultarCorreos() {
		ResultSet resultado;
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT * FROM Direcciones");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				System.out.println("ID: "+resultado.getInt("idCorreo")+" Correo: "+resultado.getString("correo"));
				System.out.println("-----------------------------------------");
			}			
		} catch (Exception e) {
			System.out.println("Error al listar los correos ");
		}
	}
}
