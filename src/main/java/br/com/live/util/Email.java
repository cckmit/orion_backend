package br.com.live.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class Email {
	public static void sendEmail(String assunto, String corpoEmail, String destinatario) {
		
		String emailAuth = "notificacao.orion@liveoficial.com.br";
		String senhaAuth = "KTt1a9wAVTzs";
		
		HtmlEmail emailHtml = new HtmlEmail();
		emailHtml.setHostName("smtp.liveoficial.com.br");
		emailHtml.setSmtpPort(587);
		emailHtml.setAuthenticator(new DefaultAuthenticator(emailAuth, senhaAuth));
		emailHtml.setSSLOnConnect(true);
		
		try {
			emailHtml.setFrom(emailAuth);
			emailHtml.setSubject(assunto);
			emailHtml.setHtmlMsg("<html> " + corpoEmail + "</html>");
			emailHtml.addTo(destinatario);
			emailHtml.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
