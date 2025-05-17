package jp.keisekisya.webapi.ctrl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jp.keisekisya.webapi.dto.LoginRequest;
import jp.keisekisya.webapi.dto.ResponseDto;
import jp.keisekisya.webapi.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

	private final AuthService service;

	private static final String PATH = "/auth/login";

	/**
	 * ログイン処理
	 * 
	 * @param dto
	 */
	@PostMapping(PATH)
	public ResponseDto login(@Valid @RequestBody LoginRequest dto) {
		return service.login(dto);
	}
}
