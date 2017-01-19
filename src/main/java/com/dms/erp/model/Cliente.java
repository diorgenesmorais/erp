package com.dms.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.dms.erp.model.validation.ClienteGroupSeguenceProvider;
import com.dms.erp.model.validation.group.CnpjGroup;
import com.dms.erp.model.validation.group.CpfGroup;

@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSeguenceProvider.class)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 3534895992732389937L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	@Column(length = 64)
	private String nome;

	@NotNull(message = "Tipo pessoa é obrigatorio")
	@Enumerated(EnumType.STRING)
	@Column(length = 8, name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	/*
	 * devido ao GroupSequenceProvider as anotações CPF e CNPJ só seram
	 * validadas após as outras validações passarem na validação.
	 */
	@NotBlank(message = "CPF/CNPJ é obrigatório")
	@CPF(groups = { CpfGroup.class })
	@CNPJ(groups = { CnpjGroup.class })
	@Column(length = 14, name = "cpf_cnpj")
	private String cpfOuCnpj;

	@Column(length = 11)
	private String telefone;

	@Email(message = "E-mail inválido")
	@Column(length = 64)
	private String email;

	@Embedded
	private Endereco endereco;

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

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOrCnpj) {
		this.cpfOuCnpj = cpfOrCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@PrePersist
	@PreUpdate
	private void beforePersistingAndUpdating() {
		this.cpfOuCnpj = TipoPessoa.removeFormatting(getCpfOuCnpj());
		this.telefone = getTelefone() != null ? getTelefone().replaceAll("\\(|\\)|\u0020|-", "") : null;
		this.endereco.setCep(this.endereco.getCep() != null ? this.endereco.getCep().replaceAll("\\.|-", "") : null);
	}

	@Transient
	public String getCpfOrCnpjWithoutFormatting() {
		return TipoPessoa.removeFormatting(getCpfOuCnpj());
	}

	@PostLoad
	private void afterLoading() {
		this.cpfOuCnpj = this.tipoPessoa.formatDocument(this.getCpfOuCnpj());
		this.telefone = getTelefone() != null
				? getTelefone().replaceAll("^(\\d{2})", "($1)\u0020").replaceAll("(\\d{4})$", "-$1") : null;
		this.endereco.setCep(this.endereco.getCep() != null
				? this.endereco.getCep().replaceAll("(\\d{2})(\\d{3})", "$1.$2-") : null);
	}
}
