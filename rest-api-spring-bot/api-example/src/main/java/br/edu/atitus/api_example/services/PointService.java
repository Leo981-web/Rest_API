package br.edu.atitus.api_example.services;

import java.util.List;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_example.dtos.PointDTO;
import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.PointRepository;
import jakarta.transaction.Transactional;

@Service
public class PointService {

	private final PointRepository repository;

	public PointService(PointRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Transactional
	public PointEntity save(PointEntity point) throws Exception {
		
		if(point == null)
			throw new Exception("Objeto nulo!");
		
		if(point.getDescription() == null || point.getDescription().isEmpty())
			throw new Exception("Descrição Inválida");
		
		if(point.getLatitude() < -90 || point.getLatitude() > 90)
			throw new Exception("Latitude Inválida");
		
		if(point.getLongitude() < -180 || point.getLongitude() > 180)
			throw new Exception("Longtude Inválida");
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		point.setUser(userAuth);
		
		return repository.save(point);
	}
	
	@Transactional
	public void deleteById(UUID id) throws Exception {
		
		var pointInBD = repository.findById(id).orElseThrow(() -> new Exception("Ponto não Localizado"));
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!pointInBD.getUser().getId().equals(userAuth.getId()))
			throw new Exception("Você não tem autorização para fazer essa ação");
		
		repository.deleteById(id);
	}
	
	private PointDTO convertToDTO(PointEntity point) {
		return new PointDTO(
				 	point.getLatitude(),
			        point.getLongitude(),
			        point.getDescription(),
			        point.isFavorite()
			    );				
	}
	
	@Transactional
	public java.util.List<PointEntity> findAll(){
		List<PointEntity> pointsList = repository.findAll();
		
		return pointsList;		
	}
	
	@Transactional
	public PointDTO setFavoriteStatus(UUID pointId, boolean isFavorite) throws Exception {
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		PointEntity point = repository.findByIdAndUser(pointId, userAuth)
				.orElseThrow(() -> new Exception("Ponto não Encontrado ou não Pertence a este usuário"));
		
		point.setFavorite(isFavorite);
		
		PointEntity savedPoint = repository.save(point);
		
		return convertToDTO(savedPoint);
	}
	
	public PointEntity update(UUID id, PointDTO dto) throws Exception {
	    PointEntity entity = repository.findById(id)
	            .orElseThrow(() -> new Exception("Ponto não encontrado"));
	    UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (!entity.getUser().getId().equals(userAuth.getId())) {
	        throw new Exception("Você não tem permissão para alterar este ponto.");
	    }
	    entity.setLatitude(dto.getLatitude());
	    entity.setLongitude(dto.getLongitude());
	    entity.setDescription(dto.getDescription());
	    return repository.save(entity);
	}
}

