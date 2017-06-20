package com.dms.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.Cervejas;
import com.dms.erp.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);

		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}

	public List<Cerveja> getCervejas() {
		return this.cervejas.findAll();
	}
}
