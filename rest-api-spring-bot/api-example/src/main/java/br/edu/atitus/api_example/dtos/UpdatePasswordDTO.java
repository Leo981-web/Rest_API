package br.edu.atitus.api_example.dtos;

public record UpdatePasswordDTO(
		
		String currentPassword,
		String newPassword
		
		) {
}
