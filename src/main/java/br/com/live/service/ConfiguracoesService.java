package br.com.live.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracoesService {
	
	@Value("${app.url.front-end}")
    private String urlFrontEnd;
    
	@Value("${app.email.smtp}")
	private String smtpEmail;
	
	@Value("${app.email.porta}")
	private String portaEmail;

	@Value("${app.email.remetente}")
	private String remetenteEmail;
	
	@Value("${app.email.senha}")
	private String senhaEmail;
	
	public String getUrlFrontEnd() {		
		return urlFrontEnd;
	}

	public String getSmtpEmail() {		
		return smtpEmail;
	}

	public String getPortaEmail() {		
		return portaEmail;
	}

	public String getRemetenteEmail() {		
		return remetenteEmail;
	}

	public String getSenhaEmail() {		
		return senhaEmail;
	}		
}
