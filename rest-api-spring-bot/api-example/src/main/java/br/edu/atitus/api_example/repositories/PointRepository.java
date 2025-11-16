package br.edu.atitus.api_example.repositories;


import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.entities.UserEntity;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, UUID> {

	
	List<PointEntity> findByUser(UserEntity user);
	
	
	Optional<PointEntity> findByIdAndUser(UUID id, UserEntity user);
}

