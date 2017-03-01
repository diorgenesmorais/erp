package com.dms.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Venda;
import com.dms.erp.repository.Vendas;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;
	
	@Transactional
	public void salvar(Venda venda) {
		vendas.save(venda);
	}
}
