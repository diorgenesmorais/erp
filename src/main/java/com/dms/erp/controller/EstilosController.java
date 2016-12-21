package com.dms.erp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.model.DetailedError;
import com.dms.erp.model.Estilo;
import com.dms.erp.service.CadastroEstiloService;
import com.dms.erp.service.exception.RegisteredAlreadyException;

@Controller
@RequestMapping("/estilo")
public class EstilosController {

	@Autowired
	private CadastroEstiloService cadastroEstiloService;

	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/CadastroEstilo");
	}

	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(estilo);
		}

		try {
			cadastroEstiloService.salvar(estilo);
		} catch (RegisteredAlreadyException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}

		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		return new ModelAndView("redirect:/estilo/novo");
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			DetailedError error = new DetailedError();
			error.setStatus(406L);
			error.setTimestamp(System.currentTimeMillis());
			error.setResponseText(result.getFieldError("nome").getDefaultMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}

		estilo = cadastroEstiloService.salvar(estilo);
		return ResponseEntity.ok(estilo);
	}
}
