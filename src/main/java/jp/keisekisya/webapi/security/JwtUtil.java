package jp.keisekisya.webapi.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	/** シークレットキー */
	@Value("${jwt.secret:your_secret_key_here}")
	private String secret;
	private SecretKey secretKey;

	/** トークン期限切れ(分) */
	@Value("${jwt.expiration:10}")
	private int expiration;

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiration)).signWith(secretKey)
				.compact();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
