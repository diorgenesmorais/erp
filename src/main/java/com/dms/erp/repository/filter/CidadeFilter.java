package com.dms.erp.repository.filter;

import com.dms.useful.UFBrasil;

public class CidadeFilter {

	private String nome;
	private UFBrasil estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UFBrasil getEstado() {
		return estado;
	}

	public void setEstado(UFBrasil estado) {
		this.estado = estado;
	}
}
