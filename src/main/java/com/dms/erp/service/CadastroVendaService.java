package com.dms.erp.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}

		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(),
					venda.getHorarioEntrega() == null ? LocalTime.NOON : venda.getHorarioEntrega()));
		}

		vendas.save(venda);
	}
}
