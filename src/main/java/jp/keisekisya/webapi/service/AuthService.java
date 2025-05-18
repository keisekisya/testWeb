package jp.keisekisya.webapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import jp.keisekisya.webapi.dto.LoginRequest;
import jp.keisekisya.webapi.dto.LoginResponse;
import jp.keisekisya.webapi.handler.BusinessException;
import jp.keisekisya.webapi.security.JwtUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	/**
	 * ログイン処理
	 * 
	 * @param dto
	 */
	public LoginResponse login(final LoginRequest dto) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BusinessException("B00004", HttpStatus.UNAUTHORIZED);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());

		return new LoginResponse(jwt);
	}

}
