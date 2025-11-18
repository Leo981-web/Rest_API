package br.edu.atitus.api_example.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;



public record SignupDTO(
		
		String name,
		String email,
		String password,
		String phoneNumber,
		
		@JsonFormat(pattern = "dd/MM/yyyy")
		LocalDate birthDate

		) {
	
}
