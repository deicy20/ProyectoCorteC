package edu.cecar.logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import edu.cecar.persistencia.Conexion;

public class EnviarReporte {
	
	Connection con = new Conexion().getConnection();
	
	/**
	 * Este metodo obtiene las direciones de correo que estan en la base de datos
	 * @return todos los correos en la base de datos
	 */
	public ArrayList<InternetAddress> getDestinatarios() {
		ArrayList<InternetAddress> destinos = new ArrayList<InternetAddress>();
		ResultSet resultado;
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT * FROM Direcciones");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				destinos.add(new InternetAddress(resultado.getString("correo")));

			}			
		} catch (Exception e) {
			System.out.println("Error al otener los correos ");
		}
		
		return destinos;
	}
	/**
	 * 
	 * @param destinatarios
	 */
	public void mandarCorreo(ArrayList<InternetAddress> destinos) {
		InternetAddress[] destinatarios = new InternetAddress[destinos.size()];
		for (int i = 0; i < destinos.size(); i++) {
			destinatarios[i] = destinos.get(i);
		}
		// El correo gmail de envío
		String correoEnvia = "lamope123456@gmail.com";
		String claveCorreo = "12345678deicy";

		// La configuración para enviar correo
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", correoEnvia);
		properties.put("mail.password", claveCorreo);

		// Obtener la sesion
		Session session = Session.getInstance(properties, null);

		try {
			// Crear el cuerpo del mensaje
			MimeMessage mimeMessage = new MimeMessage(session);

			// Agregar quien envía el correo
			mimeMessage.setFrom(new InternetAddress(correoEnvia, "sCoronavirus"));

			// Los destinatarios
			
			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO,
					destinatarios);

			// Agregar el asunto al correo
			mimeMessage.setSubject("Reporte sCoronavirus.");

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText("Adjunto el archivo con el reporte.");
			MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
			mimeBodyPartAdjunto.attachFile("src/PDFsProcesados/reporte.xlsx");

			// Crear el multipart para agregar la parte del mensaje anterior
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(mimeBodyPartAdjunto);

			// Agregar el multipart al cuerpo del mensaje
			mimeMessage.setContent(multipart);

			// Enviar el mensaje
			Transport transport = session.getTransport("smtp");
			transport.connect(correoEnvia, claveCorreo);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
