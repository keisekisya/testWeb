package jp.keisekisya.webapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.keisekisya.webapi.entity.pk.MstLoginPk;
import jp.keisekisya.webapi.handler.BusinessException;
import jp.keisekisya.webapi.repository.MstLoginRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
	private final MstLoginRepository mstLoginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		val opt = mstLoginRepository.findByPk(new MstLoginPk(username));
		if (opt.isEmpty()) {
			throw new BusinessException("B00005", HttpStatus.UNAUTHORIZED);
		}

		return User.withUsername(username).password(opt.get().getKey()).roles("USER").build();
	}
}
