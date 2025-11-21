package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.edu.atitus.api_example.components.AuthTokenFilter;

@Configuration //Indica que e uma classe de configuracao e contem metodos que criam Beans.
public class ConfigSecurity {
	
	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http, AuthTokenFilter authTokenFilter) throws Exception {
		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Desabilita secoess
		.csrf(csrf -> csrf.disable())// Desabilita protecao CSRF
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.OPTIONS).permitAll()
				.requestMatchers("/ws**","/ws/**").authenticated()
				.anyRequest().permitAll())
				.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		
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
				registry.addMapping("/**")
					.allowedOrigins("*") 
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
					.allowedHeaders("*");
			}
		};
	}
}
