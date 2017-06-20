package com.dms.erp.service.event.cerveja;

import org.thymeleaf.util.StringUtils;

import com.dms.erp.model.Cerveja;

public class CervejaSalvaEvent {

	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}

	public boolean hasFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
}
