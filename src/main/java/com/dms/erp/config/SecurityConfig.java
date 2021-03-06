package com.dms.erp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dms.erp.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Need of {@code ComponentScan}
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/layout/**")
			.antMatchers("/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * .hasRole("CADASTRAR_CIDADE") na realidade espera do banco ROLE_CADASTRAR_CIDADE
		 * 
		 * <pre>
		 * Adicionar o código abaixo (...) para expirar a sessão com um novo login
		 * e redirecionar para uma URL
		 * http.authorizeRequests()
		 * 		...
		 * 		.and()
		 * 	.sessionManagement()
		 * 		.maximumSessions(1)
		 * 		.expiredUrl("/login");
		 * 
		 * Se a sessão estiver inválida acrescer no 
		 * 	.sessionManagement()
		 * 		.invalidSessionUrl("/login")
		 * para que ele redirecione para a tela de login
		 * </pre>
		 */
		http.authorizeRequests()
				.antMatchers("/cidades/nova").hasAuthority("CADASTRAR_CIDADE")
				.antMatchers("/usuarios/**").hasAuthority("CADASTRAR_USUARIO")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login")
				.maximumSessions(1)
				.expiredUrl("/login");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
