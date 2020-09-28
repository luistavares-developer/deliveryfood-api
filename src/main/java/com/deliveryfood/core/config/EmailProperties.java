package com.deliveryfood.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("deliveryfood.email")
public class EmailProperties {

	private String remetente;
	private Implementacao impl = Implementacao.FAKE;
	private Sandbox sandbox = new Sandbox();

	
	public enum Implementacao {
	    SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class Sandbox {
	    
	    private String destinatario;
	    
	}   
}
