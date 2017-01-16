package com.dms.erp.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarios.byEmailAndActive(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário não é ativo ou não encontrado"));
		return new User(usuario.getEmail(), usuario.getPassword(), gettingPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> gettingPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authority = new HashSet<>();

		List<String> permissoes = usuarios.getPermissoes(usuario);
		permissoes.forEach(p -> authority.add(new SimpleGrantedAuthority(p)));
		permissoes.forEach(p -> System.out.println(">>>>> " + p));

		return authority;
	}

}
