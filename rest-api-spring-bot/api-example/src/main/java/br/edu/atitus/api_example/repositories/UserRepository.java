package br.edu.atitus.api_example.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity, UUID>{
	
	// Basicamente um comando sql sendo o comando select * from tb_user where email
	boolean exixtsByEmail(String email);
	
	// Basicamente um comando sql sendo o comando select * from tb_user where email = {} and name = {}
	boolean exixtsByAndEmail(String email, String name);
		
	Optional<UserEntity> findByEmail(String email);

	
}
