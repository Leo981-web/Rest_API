package br.edu.atitus.api_example.dtos;

import java.util.UUID;

public record PointResponseDTO(
		
		UUID id,
		String descrption,
		double latitude, 
	    double longitude, 
	    UUID userId
		) {
	
}
