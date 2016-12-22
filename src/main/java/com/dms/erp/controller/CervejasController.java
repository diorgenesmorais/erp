package com.dms.erp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.model.Cerveja;
import com.dms.erp.model.Origem;
import com.dms.erp.model.Sabor;
import com.dms.erp.repository.Estilos;
import com.dms.erp.service.CadastroCervejaService;

@Controller
@RequestMapping({ "/cervejas" })
public class CervejasController {
	
	@Autowired
	private Estilos estilos;
	@Autowired
	private CadastroCervejaService cadastroCervejaService;

	@GetMapping({ "/novo" })
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", this.estilos.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}

	@PostMapping({ "/novo" })
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult bindingResult, RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return novo(cerveja);
		}
		this.cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");

		return new ModelAndView("redirect:/cervejas/novo");
	}

	@GetMapping
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("cerveja/pesquisaCervejas");
		mv.addObject("estilos", this.estilos.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());

		mv.addObject("cervejas", this.cadastroCervejaService.getCervejas());

		return mv;
	}
}