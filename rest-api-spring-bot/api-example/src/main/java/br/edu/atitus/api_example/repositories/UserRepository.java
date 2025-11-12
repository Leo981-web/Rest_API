package br.edu.atitus.api_example.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity, UUID>{
	
	// Basicamente um comando SQL sendo o comando select * from tb_user where email
	boolean existsByEmail(String email);
	
	// Basicamente um comando SQL sendo o comando select * from tb_user where email = {} and name = {}
	boolean existsByAndEmail(String email, String name);
		
	Optional<UserEntity> findByEmail(String email);

	
}
