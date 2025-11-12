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
		
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new Exception("Nome inválido.");
		}
		user.setName(user.getName().trim());
		
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new Exception("Email inválido.");
		}
		user.setName(user.getEmail().trim());
		
		if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 8) {
			throw new Exception("Senha inválida.");
		}
		user.setPassword(enconder.encode(user.getPassword()));
		
		if (repository.exixtsByEmail(user.getEmail()))
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
