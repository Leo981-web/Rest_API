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
	
	private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
	
	public UserService(UserRepository repository, PasswordEncoder enconder) {
		super();
		this.repository = repository;
		this.enconder = enconder;
	}

	public UserEntity save(UserEntity user) throws Exception {
        if (user == null)
            throw new Exception("Objeto Nulo");

        if (user.getName() == null || user.getName().isEmpty())
            throw new Exception("Nome inválido");
        user.setName(user.getName().trim());
		
        if(user.getEmail() == null || !user.getEmail().matches(EMAIL_REGEX))
        	throw new Exception("E-mail inválido. O formato deve ser 'usuario@dominio.com'");
        user.setEmail(user.getEmail().trim());
        
        if (user.getPassword() == null || !user.getPassword().matches(PASSWORD_REGEX))
            throw new Exception("Senha inválida. A senha deve ter no mínimo 8 caracteres, "
                    + "conter pelo menos uma letra maiúscula, uma minúscula e um número.");
        user.setPassword(enconder.encode(user.getPassword()));
        
        if (repository.existsByEmail(user.getEmail()))
            throw new Exception("Já existe usuário cadastrado com este e-mail");
        
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