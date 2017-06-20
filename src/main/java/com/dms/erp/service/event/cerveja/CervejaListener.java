package com.dms.erp.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.dms.erp.storage.FotoStorage;

@Component
public class CervejaListener {

	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#event.hasFoto()")
	public void cervejaSalva(CervejaSalvaEvent event) {
		fotoStorage.salvar(event.getCerveja().getFoto());
	}
}
