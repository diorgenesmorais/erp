package com.dms.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.Cervejas;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;

	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	}
}
