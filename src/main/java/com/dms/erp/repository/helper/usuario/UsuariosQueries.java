package com.dms.erp.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> byEmailAndActive(String email);

	public List<Usuario> filtrar(UsuarioFilter filter);
	
	public void changeActiveByIds(boolean active, Long[] ids);

}
