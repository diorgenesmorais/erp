package com.dms.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class ClientesController {

	@GetMapping("/novo")
	public ModelAndView novo() {
		return new ModelAndView("cliente/cadastroCliente");
	}

}
