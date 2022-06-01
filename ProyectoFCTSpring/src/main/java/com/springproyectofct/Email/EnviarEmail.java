package com.springproyectofct.Email;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarEmail {


	

	public void enviarConGMail(String destinatario, String asunto, String cuerpo) {
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente tambi�n.
		String remitente = "proyectomiredsocial@gmail.com"; // Para la direccion nomcuenta@gmail.com

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.googlemail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", "Baldomera3."); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticacion mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipients(Message.RecipientType.TO, destinatario); // Se podran añadir varios de la misma
																			// manera
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.googlemail.com", remitente, "Baldomera3.");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
		}
	}

}
