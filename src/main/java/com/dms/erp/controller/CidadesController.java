package com.dms.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CidadesController {

	@GetMapping("/cidades/nova")
	public ModelAndView nova() {
		return new ModelAndView("cidades/cadastroCidade");
	}
}
