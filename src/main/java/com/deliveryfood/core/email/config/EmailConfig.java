package com.deliveryfood.core.email.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.core.email.EmailService;
import com.deliveryfood.core.email.FakeEnvioEmailService;
import com.deliveryfood.core.email.SandBoxEnvioEmailService;
import com.deliveryfood.core.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandBoxEnvioEmailService();
            default:
                return null;
        }
    }
}       
