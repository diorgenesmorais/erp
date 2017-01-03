package com.dms.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dms.erp.model.Cidade;
import com.dms.erp.repository.Cidades;
import com.dmsystem.useful.UFBrasil;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	private Cidades cidades;

	@GetMapping("/nova")
	public ModelAndView nova() {
		return new ModelAndView("cidades/cadastroCidade");
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorEstado(@RequestParam UFBrasil estado) {

		return cidades.findByEstado(estado);
	}
}
