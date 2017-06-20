package com.dms.erp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Cidade;
import com.dms.erp.repository.Cidades;
import com.dms.erp.service.exception.RegisteredAlreadyException;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;

	@Transactional
	public void salvar(Cidade cidade) {
		Optional<Cidade> cid = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if (cid.isPresent()) {
			throw new RegisteredAlreadyException("Cidade já cadastrada");
		}
		cidades.save(cidade);
	}
}
