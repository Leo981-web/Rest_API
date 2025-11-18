package br.edu.atitus.api_example.dtos;

public record PointDTO(double latitude, double longitude, String description, boolean favorite) {

	 public Double getLatitude() {
	       return latitude;
	   }
	   public Double getLongitude() {
	       return longitude;
	   }
	   public String getDescription() {
	       return description;
	   }
}