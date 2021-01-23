package com.dms.erp.model;

public enum StatusVenda {

	ORCAMENTO("Orçamento"), EMITIDO("Emitido"), CANCELADO("Cancelado");

	String description;

	StatusVenda(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
