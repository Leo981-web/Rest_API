package br.edu.atitus.api_example.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupDTO(
		
		@NotBlank(message = "O nome não pode estar vazio")
		@Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
		String name,
		
		@NotBlank(message = "O e-mail não pode estar em branco.")
		@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$", 
		message = "E-mail inválido. Deve seguir o formato usuario@dominio.com ou usuario@dominio.com.br")
		String email,
		
		@NotBlank(message = "A senha não pode estar em branco.")
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", 
        message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número.")
		@Size(min = 6, message = "A Senha deve conter pelo menos 6 caracteres")
		String password) {
	
}
