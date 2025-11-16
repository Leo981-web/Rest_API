package br.edu.atitus.api_example.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private final UserRepository repository;
	private final PasswordEncoder enconder;
	
	public UserService(UserRepository repository, PasswordEncoder enconder) {
		super();
		this.repository = repository;
		this.enconder = enconder;
	}

	public UserEntity save(UserEntity user) throws Exception {
		
		if (user == null) {
			throw new Exception("Objeto Nulo.");
		}
	
		user.setName(user.getName().trim());
		user.setEmail(user.getEmail().trim());
		user.setPassword(enconder.encode(user.getPassword())); //Pega senha com texto puro, codifica e coloca como "nova' senha.
		
		if (repository.existsByEmail(user.getEmail()))
			throw new Exception ("Já existe usuário cadastrado com este email.");
		
		repository.save(user);
		
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Não existe usuário com este email."));
				
		return user;
	}
}
