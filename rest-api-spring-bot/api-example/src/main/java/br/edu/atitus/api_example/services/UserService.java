package br.edu.atitus.api_example.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}(\\\\.[a-zA-Z]{2,})*$";
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
        
        if(user.getPhoneNumber() == null || !user.getPhoneNumber().matches("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$"))
        	throw new Exception("Telefone inválido. Use o formato (XX)9XXXX-XXXX ou similar");
        
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())
            throw new Exception("Número de telefone inválido. Campo obrigatório.");
        
        if (user.getBirthDate() == null) 
            throw new Exception("Data de nascimento inválida. Campo obrigatório.");
        
        if (user.getBirthDate() == null)
        	throw new Exception("Data de nascimento obrigatória");
        
        if (user.getBirthDate().isAfter(java.time.LocalDate.now().minusYears(10))) 
            throw new Exception("Usuário deve ter no mínimo 10 anos de idade");
        
        repository.save(user);
        
        return user;
        
		}
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			var user = repository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Não existe usuário com este email."));
					
			return user;
		}
		
		public void updatePassword(String currentPassword, String newPassword) throws Exception {
        	UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	
        	UserEntity userFromBD = repository.findById(user.getId())
        			.orElseThrow(() -> new Exception("Usuario não encontrado"));
        	
        	if(!enconder.matches(currentPassword, userFromBD.getPassword())) 
        		throw new Exception("A senha atual está incorreta");
        	
        	
        	if (!newPassword.matches(PASSWORD_REGEX)) 
                throw new Exception("Nova senha inválida. Deve conter letras maiúsculas, minúsculas e números.");

        	
        	userFromBD.setPassword(enconder.encode(newPassword));
        	repository.save(userFromBD);
        }

	}