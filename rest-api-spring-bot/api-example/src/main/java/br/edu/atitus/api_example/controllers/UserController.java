package br.edu.atitus.api_example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.dtos.UpdatePasswordDTO;
import br.edu.atitus.api_example.services.UserService;

@RestController
@RequestMapping("/ws/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PutMapping("/password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO dto){
		
		try {
			userService.updatePassword(dto.currentPassword(), dto.newPassword());
			return ResponseEntity.ok("Senha alterada com sucesso");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
