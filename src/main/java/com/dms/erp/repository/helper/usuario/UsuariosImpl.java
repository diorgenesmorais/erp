package com.dms.erp.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dms.erp.model.Usuario;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Usuario> byEmailAndActive(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and active = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

}
