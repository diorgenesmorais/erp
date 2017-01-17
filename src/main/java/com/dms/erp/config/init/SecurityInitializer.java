package com.dms.erp.config.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	// corrigindo o problema de encode devido a implementação do Spring Security
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		FilterRegistration.Dynamic characterEncoderFilter = servletContext.addFilter("encoderFilter"
				, new CharacterEncodingFilter());
		characterEncoderFilter.setInitParameter("encoding", "UTF-8");
		characterEncoderFilter.setInitParameter("forceEncoding", "true");
		characterEncoderFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
