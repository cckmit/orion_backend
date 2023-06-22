package br.com.live.util.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	
	private final ConfiguracoesService configuracoes;
	
	public EmailService(ConfiguracoesService configuracoes) {
		this.configuracoes = configuracoes;
	}
	
	public void enviar(String assunto, String corpoEmail, String destinatario) {
		
		
		String emailAuth = configuracoes.getRemetenteEmail();
		String senhaAuth = configuracoes.getSenhaEmail();
		
		HtmlEmail emailHtml = new HtmlEmail();
		emailHtml.setHostName(configuracoes.getSmtpEmail());
		emailHtml.setSmtpPort(Integer.parseInt(configuracoes.getPortaEmail()));
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
