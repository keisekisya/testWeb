package jp.keisekisya.webapi.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MessageUtils {
	private final MessageSource messageSource;

	/**
	 * エラーメッセージ取得
	 * 
	 * @param code コード
	 * @return メッセージ
	 */
	public String getMessage(final String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}
}
