package com.dms.erp.model;

public enum TipoPessoa {

	FISICA("Física", "CPF", "000.000.000-00"), 
	JURIDICA("Juridica", "CNPJ", "00.000.000/0000-00");

	private String decription;
	private String document;
	private String mask;

	private TipoPessoa(String decription, String document, String mask) {
		this.decription = decription;
		this.document = document;
		this.mask = mask;
	}

	public String getDecription() {
		return decription;
	}

	public String getDocument() {
		return document;
	}

	public String getMask() {
		return mask;
	}
}
