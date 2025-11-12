package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Indica que é uma classe de configuração e contém métodos que criam Beans.
public class ConfigSecurity {
	
	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Desabilita seções
		.csrf(csrf -> csrf.disable())// Desabilita proteção CSRF
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/ws**","/ws/**").authenticated()
				.anyRequest().permitAll());
		
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder getPasswordEnconder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) { //Qualquer site pode fazer requisicoes para essa API, o que nao e seguro em proj maiores.
				registry.addMapping("/**").allowedOrigins("*"); //Pode se mudar o '*' pela URL do site que tem acesso, caso necessario.
			}
		};
	}
}
