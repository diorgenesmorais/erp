package com.dms.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dms.erp.model.TipoPessoa;
import com.dmsystem.useful.UFBrasil;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("cliente/cadastroCliente");
		mv.addObject("typesOfPeople", TipoPessoa.values());
		mv.addObject("states", UFBrasil.values());

		return mv;
	}

}
