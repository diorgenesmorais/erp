package com.dms.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.controller.page.PageWrapper;
import com.dms.erp.model.Estilo;
import com.dms.erp.repository.Estilos;
import com.dms.erp.repository.filter.EstiloFilter;
import com.dms.erp.service.CadastroEstiloService;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.erp.service.exception.ValidationException;

@Controller
@RequestMapping({ "/estilos" })
public class EstilosController {

	@Autowired
	private CadastroEstiloService estiloService;
	@Autowired
	private Estilos estilos;

	@RequestMapping({ "/novo" })
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/cadastroEstilo");
	}

	@RequestMapping(value = { "/novo" }, method = { RequestMethod.POST })
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(estilo);
		}
		try {
			this.estiloService.salvar(estilo);
		} catch (RegisteredAlreadyException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
		return new ModelAndView("redirect:/estilo/novo");
	}

	@RequestMapping(method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			throw new ValidationException(result.getFieldError("nome").getDefaultMessage());
		}
		estilo = this.estiloService.salvar(estilo);

		return ResponseEntity.status(HttpStatus.CREATED).body(estilo);
	}

	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("estilo/pesquisaEstilos");

		PageWrapper<Estilo> page = new PageWrapper<>(estilos.filtrar(estiloFilter, pageable), httpServletRequest);
		mv.addObject("pagina", page);
		return mv;
	}
}
