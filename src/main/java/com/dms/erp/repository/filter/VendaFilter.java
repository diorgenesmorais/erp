package com.dms.erp.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dms.erp.model.Cliente;
import com.dms.erp.model.StatusVenda;

public class VendaFilter {

	private Long id;
	private StatusVenda statusVenda;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private BigDecimal valorTotalMinimo;
	private BigDecimal valorTotalMaximo;
	private Cliente cliente;
	private String cpfOrCnpj;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public BigDecimal getValorTotalMinimo() {
		return valorTotalMinimo;
	}

	public void setValorTotalMinimo(BigDecimal valorTotalMinimo) {
		this.valorTotalMinimo = valorTotalMinimo;
	}

	public BigDecimal getValorTotalMaximo() {
		return valorTotalMaximo;
	}

	public void setValorTotalMaximo(BigDecimal valorTotalMaximo) {
		this.valorTotalMaximo = valorTotalMaximo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

}
