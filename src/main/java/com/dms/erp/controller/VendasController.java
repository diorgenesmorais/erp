package com.dms.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.Cervejas;
import com.dms.erp.session.TabelaItensVenda;

@Controller
@RequestMapping({ "/vendas" })
public class VendasController {

	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView("venda/cadastroVenda");

		return mv;
	}
	
	@PostMapping("/item")
	public @ResponseBody String adicionarItem(Long id){
		Cerveja cerveja = cervejas.findOne(id);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		return "item adicionado: " + cerveja.getNome() + " valor total: " + tabelaItensVenda.getValorTotal();
	}
}
