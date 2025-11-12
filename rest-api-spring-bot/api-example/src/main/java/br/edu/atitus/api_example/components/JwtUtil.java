package br.edu.atitus.api_example.components;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

public class JwtUtil {
	private static final String SECRET_KEY = "ChaveSuperHiperMegaUltraRadicalGigaColossalSecreta123"; //Chave secreta para assinar os tokens => Fins didáticos, não se deixa pública.
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; //Milisegs -> segs -> mins -> horas.
	
	private static SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public static String generateToken(String email) { //Gera o Token.
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSecretKey())
				.compact();
	}
	
	public static String getJwtFromRequest(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		if (jwt == null || jwt.isEmpty()) {
			jwt = request.getHeader("authorization");
		}
		
		if (jwt != null && !jwt.isEmpty()) {
			return jwt.substring(7); //Pega a partir da setima posicao.
		}
		return null;
	}

	public static String validateToken(String jwt) {
		try {
			return Jwts.parser()
					.verifyWith(getSecretKey())
					.build()
					.parseSignedClaims(jwt)
					.getPayload().getSubject();
		} catch (Exception e) {
			return null;
		}
	}
}
