package com.dms.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dms.erp.config.WebConfig;
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
	public ModelAndView adicionarItem(Long id) {
		Cerveja cerveja = cervejas.findOne(id);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		return getTabelaItensVenda();
	}

	@PutMapping("/item/{id}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long id, Integer quantidade) {
		Cerveja cerveja = cervejas.findOne(id);
		tabelaItensVenda.alterarQuantidadeItem(cerveja, quantidade);
		return getTabelaItensVenda();
	}

	/**
	 * Remove o item baseado na cerveja.
	 * 
	 * <pre>
	 * Criei um {@code Bean} {@link WebConfig#domainClassConverter()} 
	 * que busca do domínio o objeto informado no parâmetro baseado no
	 * {@code PathVariable("id")}:
	 * 
	 * Cerveja cerveja = cervejas.findOne(id);
	 * 
	 * Entregando o parâmetro com o retorno deste (findOne).
	 * </pre>
	 * 
	 * @param cerveja objeto do domínio.
	 * @return {@code ModelAndView}
	 */
	@DeleteMapping("/item/{id}")
	public ModelAndView removerItem(@PathVariable("id") Cerveja cerveja) {
		tabelaItensVenda.removeItem(cerveja);
		return getTabelaItensVenda();
	}

	private ModelAndView getTabelaItensVenda() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		mv.addObject("valorTotal", tabelaItensVenda.getValorTotal());
		return mv;
	}
}
