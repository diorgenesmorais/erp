package com.dms.erp.venda;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.dms.erp.model.Cerveja;
import com.dms.erp.session.TabelaItensVenda;

public class TabelaItensVendaTest {

	private TabelaItensVenda tabelaItensVenda;
	private Cerveja cerveja;

	@Before
	public void setUp() {
		tabelaItensVenda = new TabelaItensVenda();
		cerveja = new Cerveja();
	}

	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}

	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setId(1L);
		cerveja.setValor(valor);

		tabelaItensVenda.adicionarItem(cerveja, 1);

		assertEquals(valor, tabelaItensVenda.getValorTotal());
	}

	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		BigDecimal valor1 = new BigDecimal("8.9");
		BigDecimal valor2 = new BigDecimal("11.4"); // x 3 = 34,20

		cerveja.setId(1L);
		cerveja.setValor(valor1);
		Cerveja becks = new Cerveja();
		becks.setId(2L);
		becks.setValor(valor2);

		tabelaItensVenda.adicionarItem(cerveja, 1);
		tabelaItensVenda.adicionarItem(becks, 3);

		assertEquals(new BigDecimal("43.1"), tabelaItensVenda.getValorTotal());
	}

	@Test
	public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
		cerveja.setId(1L);
		cerveja.setValor(new BigDecimal("4.5"));

		tabelaItensVenda.adicionarItem(cerveja, 1);
		tabelaItensVenda.adicionarItem(cerveja, 1);

		assertEquals(1, tabelaItensVenda.getItens().size());
	}
}
