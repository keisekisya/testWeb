package jp.keisekisya.webapi.ctrl;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.val;

public abstract class BaseController {
	protected String getCurrentUsername() {
		val auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null ? auth.getName() : null;
	}
}
