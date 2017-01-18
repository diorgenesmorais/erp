package com.dms.erp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.dms.erp.model.Usuario;

/**
 * This {@code UserLoggedIn} class retains as the {@code Usuario} template
 * object's attribute for later use.
 * 
 * @author Diorgenes Morais
 * @version 1.0.0
 */
public class UserLoggedIn extends User {

	private static final long serialVersionUID = -1067888933400255669L;
	private Usuario usuario;

	public UserLoggedIn(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getPassword(), authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
