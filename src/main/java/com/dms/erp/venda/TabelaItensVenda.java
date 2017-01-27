package com.dms.erp.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dms.erp.model.ItenVenda;

public class TabelaItensVenda {

	private List<ItenVenda> itens = new ArrayList<>();

	public BigDecimal getValorTotal() {
		return itens.stream().map(ItenVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
}
