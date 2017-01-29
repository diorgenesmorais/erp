package com.dms.erp.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		Optional<ItemVenda> itemOptional = findByCerveja(cerveja);

		ItemVenda itemVenda = null;
		if (itemOptional.isPresent()) {
			itemVenda = itemOptional.get();
			itemVenda.setQtde(Integer.sum(itemVenda.getQtde(), qtde));
		} else {
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQtde(qtde);
			itemVenda.setValorUnitario(cerveja.getValor());
			// criar uma lista decrescente
			itens.add(0, itemVenda);
		}
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	private Optional<ItemVenda> findByCerveja(Cerveja cerveja) {
		return itens.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
	}
}
