package jp.keisekisya.webapi.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.keisekisya.webapi.Interceptor.LoggingInterceptor;
import lombok.AllArgsConstructor;
import lombok.val;

/**
 * Web関連の設定
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	/* ログインターセプター */
	private final LoggingInterceptor loggingInterceptor;

	/**
	 * インターセプターの設定
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/**"); // 全てのパスに適用
	}

	/**
	 * 日時関連のロケールを設定する
	 * 
	 * @return ロケール
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.JAPAN);
		return slr;
	}

	/**
	 * Jsonオブジェクトマッパー
	 * 
	 * @return Jsonマッパー
	 */
	@Bean
	public ObjectMapper objectMapper() {
		val om = new ObjectMapper();
		val jtm = new JavaTimeModule();
		om.registerModule(jtm);
		return om;
	}
}
