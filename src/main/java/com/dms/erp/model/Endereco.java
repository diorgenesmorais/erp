package com.dms.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.dmsystem.useful.UFBrasil;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 2669733476326438079L;

	@Column(length = 64)
	private String logradouro;
	
	private int numero;
	
	@Column(length = 64)
	private String complemento;
	
	@Column(length = 8)
	private String cep;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	
	@Transient
	private UFBrasil estado;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public UFBrasil getEstado() {
		return estado;
	}

	public void setEstado(UFBrasil estado) {
		this.estado = estado;
	}
	
}
