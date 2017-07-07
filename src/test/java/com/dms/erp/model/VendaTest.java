package com.dms.erp.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class VendaTest {

	private Venda venda;
	
	@Before
	public void init(){
		this.venda = new Venda();
	}
	
	@Test
	public void deveObterTotalDeVendaInicial() throws Exception {
		BigDecimal expected = BigDecimal.ZERO;
		assertEquals(expected, venda.getValorTotalItens());
	}
	
	@Test
	public void deveObterUmTotalDeVenda() throws Exception {
		BigDecimal expected = new BigDecimal(24.0);
		ItemVenda item = new ItemVenda();
		item.setQuantidade(4);
		item.setValorUnitario(new BigDecimal(6.0));
		
		venda.addItem(item);
		
		assertEquals(expected, venda.getValorTotalItens());
	}
}
