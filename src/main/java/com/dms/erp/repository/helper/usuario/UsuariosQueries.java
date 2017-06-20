package com.dms.erp.repository.helper.usuario;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> byEmailAndActive(String email);

	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
	
	public void changeActiveByIds(boolean active, Long[] ids);

}
