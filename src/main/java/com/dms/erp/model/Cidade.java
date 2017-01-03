package com.dms.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dmsystem.useful.UFBrasil;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 6594960460830067021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 40)
	private String nome;

	/*
	 * Se estado fosse um relacionamento no banco seria:
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY) e devido a requisição saber desse
	 * relacionamento o Jackson por padrão iria solicitar que fosse carregado
	 * também o estado, por isso também seria necessário anotar com:
	 * 
	 * @JsonIgnore se não seria lançado a LazyInitializationException.
	 */
	@Enumerated(EnumType.STRING)
	private UFBrasil estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
