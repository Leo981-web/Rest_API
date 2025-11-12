package br.edu.atitus.api_example.components;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.atitus.api_example.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private final UserService userService;
	
	public AuthTokenFilter(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = JwtUtil.getJwtFromRequest(request); //Verifica se tem jwt(token) na request.
		if (jwt != null) {
			String email = JwtUtil.validateToken(jwt); //Se existe, o valida e retorna um email.
			if (email != null) {
				var user = userService.loadUserByUsername(email); //Verifica se existe usuario com esse email.
				if (user != null) {
					var auth = new UsernamePasswordAuthenticationToken(user, null, null); //Se existe. autentica conecao.
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Passa autenticacao dentro da requisicao.
					SecurityContextHolder.getContext().setAuthentication(auth); //Realmente autentica.
				}
			}
		}
		
		filterChain.doFilter(request, response); //Se nao encontrar o jwt, continua com os filtros dele.
	}

}
