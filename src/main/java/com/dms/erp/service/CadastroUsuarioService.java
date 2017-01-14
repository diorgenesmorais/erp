package com.dms.erp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.Usuarios;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.erp.service.exception.SenhaUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getPassword())) {
			throw new SenhaUsuarioException("A senha é requerida");
		}

		if (usuario.isNovo()) {
			usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
			// necessário porque o JPA válida antes de persistir
			usuario.setConfirmPassword(usuario.getPassword());
		}
		return usuarios.saveAndFlush(usuario);
	}
}
