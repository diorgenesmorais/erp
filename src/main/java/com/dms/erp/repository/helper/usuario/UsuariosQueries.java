package com.dms.erp.repository.helper.usuario;

import java.util.Optional;

import com.dms.erp.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> byEmailAndActive(String email);
}
