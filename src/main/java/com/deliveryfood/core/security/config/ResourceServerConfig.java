package com.deliveryfood.core.security.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	private static final  String CHAVE_SIMETRICA = "HmacSHA256";
	private static final  byte[] KEY = "yYdsad6fydafdTTFDSA8ssdaaygasdasd092gyftdsaTF".getBytes();
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
			.and()
				.cors()
			.and()
				.oauth2ResourceServer().jwt();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec("yYdsad6fydafdTTFDSA8ssdaaygasdasd092gyftdsaTF".getBytes(), CHAVE_SIMETRICA);
		
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
}
