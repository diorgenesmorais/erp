package com.dms.erp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.Usuarios;
import com.dms.erp.service.exception.RegisteredAlreadyException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;

	/**
	 * Save a {@code Usuario} object.
	 * 
	 * @param usuario
	 *            a object of {@code Usuario}
	 * @return Returns a saved {@code Usuario} object in the database
	 */
	@Transactional
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> valueFound = usuarios.findByEmail(usuario.getEmail());
		if (valueFound.isPresent()) {
			throw new RegisteredAlreadyException("E-mail já cadastrado");
		}
		return usuarios.saveAndFlush(usuario);
	}
}
