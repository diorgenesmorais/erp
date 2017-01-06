package com.dms.erp.model;

import com.dms.erp.model.validation.group.CnpjGroup;
import com.dms.erp.model.validation.group.CpfGroup;

public enum TipoPessoa {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class), JURIDICA("Juridica", "CNPJ", "00.000.000/0000-00",
			CnpjGroup.class);

	private String decription;
	private String document;
	private String mask;
	private Class<?> group;

	private TipoPessoa(String decription, String document, String mask, Class<?> group) {
		this.decription = decription;
		this.document = document;
		this.mask = mask;
		this.group = group;
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

	public Class<?> getGroup() {
		return group;
	}

	/**
	 * Remove formatting of CPF and CNPJ.
	 * 
	 * @param formatted
	 *            with formatting.
	 * @return without formatting.
	 */
	public static String removeFormatting(String formatted) {
		return formatted != null ? formatted.replaceAll("\\.|-|/", "") : "";
	}
}
