package com.dms.erp.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.dms.erp.model.Cerveja;
import com.dms.erp.model.ItemVenda;

@SessionScope
@Component
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
