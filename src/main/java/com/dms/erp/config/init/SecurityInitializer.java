package com.dms.erp.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		/**
		 * definir em quantos segundos a sessão irá expirar independente de o
		 * usuário está mexendo ou não (1200 = 20 minutos)
		 */
		// servletContext.getSessionCookieConfig().setMaxAge(1200);

		/**
		 * impedir que o número da sessão apareça na URL
		 */
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

		/**
		 * corrigindo o problema de encode devido a implementação do Spring Security
		 */
		FilterRegistration.Dynamic characterEncoderFilter = servletContext.addFilter("encoderFilter",
				new CharacterEncodingFilter());
		characterEncoderFilter.setInitParameter("encoding", "UTF-8");
		characterEncoderFilter.setInitParameter("forceEncoding", "true");
		characterEncoderFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
