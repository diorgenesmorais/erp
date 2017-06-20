package com.dms.erp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Estilo;
import com.dms.erp.repository.Estilos;
import com.dms.erp.service.exception.RegisteredAlreadyException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;

	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new RegisteredAlreadyException("Nome do estilo já cadastrado");
		}

		return estilos.saveAndFlush(estilo);
	}
}
