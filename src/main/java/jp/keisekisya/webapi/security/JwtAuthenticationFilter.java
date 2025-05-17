package jp.keisekisya.webapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final static String SECRETKEY = "test123";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Authorizationヘッダから"Bearer xxx"を取得
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();

				// ユーザー情報をSecurityContextにセット（認証成功として扱う）
				String username = claims.getSubject();
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
						null, List.of());

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (ExpiredJwtException e) {
				// トークン期限切れ（401エラーへ）
			} catch (Exception e) {
				// トークン不正（401エラーへ）
			}
		}

		filterChain.doFilter(request, response); // 次の処理へ
	}
}
