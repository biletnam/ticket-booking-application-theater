package com.proiect.poo.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.RezervareSpectacol;

// singleton
public class TrimiteMailUtil {
	
	private static TrimiteMailUtil instance;
	private final String ADRESA_EMAIL_EXPEDITOR = "ovidius.poo.2018@gmail.com";
	
	public static TrimiteMailUtil getInstante() {
		if(instance == null) {
			instance = new TrimiteMailUtil();
		}
		
		return instance;
	}
	
	// constructor privat
	private TrimiteMailUtil() {
		
	}
	
	// citire date privind rezervarea -> trimitere email
	// updatare rezervare in fisier (false->true)
	public void trimiteEmail(RezervareSpectacol rezervareSpectacol) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ADRESA_EMAIL_EXPEDITOR, "andreeastan2018poo");
			}
		};

		Session session = Session.getDefaultInstance(props, authenticator);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ADRESA_EMAIL_EXPEDITOR));
			//destinatar
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rezervareSpectacol.getPersoana().getEmail()));
			//subiect
			message.setSubject("Confirmare rezervare spectacol " + rezervareSpectacol.getNumeSpectacol());
			
			String locuriRezervate = "";
			for (LocSpectacol locRezervat : rezervareSpectacol.getLocuriRezervate()) {
				locuriRezervate = locuriRezervate + locRezervat.getNume() + " ";
			}
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Buna ziua, ");
			stringBuilder.append(rezervareSpectacol.getPersoana().getNume());
			stringBuilder.append("\n\n");
			stringBuilder.append("Rezervarea dumneavastra este confirmata.\n\n");
			stringBuilder.append("Nume spectacol: ");
			stringBuilder.append(rezervareSpectacol.getNumeSpectacol());
			stringBuilder.append("\n");
			stringBuilder.append("Data si ora reprezentatiei: ");
			stringBuilder.append(rezervareSpectacol.getDataReprezentatie() + " " + rezervareSpectacol.getOraReprezentatie());
			stringBuilder.append("\n");
			stringBuilder.append("Locuri rezervate: ");
			stringBuilder.append(locuriRezervate + "\n\n" );
			stringBuilder.append("Vizionare placuta!");
			
			message.setText(stringBuilder.toString());

			Transport.send(message);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}