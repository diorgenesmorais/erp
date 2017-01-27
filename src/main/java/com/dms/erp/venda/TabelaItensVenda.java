package com.dms.erp.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dms.erp.model.Cerveja;
import com.dms.erp.model.ItemVenda;

public class TabelaItensVenda {

	private List<ItemVenda> itens = new ArrayList<>();

	public BigDecimal getValorTotal() {
		return itens.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void adicionarItem(Cerveja cerveja, Integer qtde) {
		ItemVenda item = new ItemVenda();
		item.setCerveja(cerveja);
		item.setQtde(qtde);
		item.setValorUnitario(cerveja.getValor());

		itens.add(item);
	}
}
