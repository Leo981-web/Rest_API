package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {
	
	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCrationPolicy.STATELESS)) // Desabilita seções
		.csrf(csrf -> csrf.disable))// cdsabilita proteção CSRF
		.authorizeHttpRequests(auth -> auth
				.requestMatches("/ws**","/ws/**").autenticate()
				.anyRequest().permiteAll());
		
		
		return http.build();
	}
	
	@Bean
	PasswordEnconder getPasswordEnconder() {
		return new PasswordEnconder;
	}
	
}
