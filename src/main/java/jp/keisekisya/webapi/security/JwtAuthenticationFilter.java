package jp.keisekisya.webapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.val;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Authorizationヘッダから"Bearer xxx"を取得
		val authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			val token = authHeader.substring(7);
			try {
				val claims = jwtUtil.getClaims(token);

				// ユーザー情報をSecurityContextにセット（認証成功として扱う）
				String username = claims.getSubject();
				val authentication = new UsernamePasswordAuthenticationToken(username, null, List.of());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				throw e;
			}
		}

		filterChain.doFilter(request, response);
	}
}
