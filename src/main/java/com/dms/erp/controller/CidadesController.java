package com.dms.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.controller.page.PageWrapper;
import com.dms.erp.model.Cidade;
import com.dms.erp.repository.Cidades;
import com.dms.erp.repository.filter.CidadeFilter;
import com.dms.erp.service.CadastroCidadeService;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.useful.UFBrasil;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	private Cidades cidades;
	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidades/cadastroCidade");
		mv.addObject("states", UFBrasil.values());
		return mv;
	}

	@Cacheable(value = "cidades", key = "#estado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorEstado(@RequestParam UFBrasil estado) {

		return cidades.findByEstado(estado);
	}

	@CacheEvict(value = "cidades", key = "#cidade.estado", condition = "#cidade.hasEstado()")
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(cidade);
		}
		try {
			cidadeService.salvar(cidade);
		} catch (RegisteredAlreadyException e) {
			result.rejectValue("nome", "1040", e.getMessage());
			return nova(cidade);
		}
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(CidadeFilter filter, BindingResult result,
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidades/pesquisaCidades");

		PageWrapper<Cidade> page = new PageWrapper<>(cidades.filtrar(filter, pageable), httpServletRequest);
		mv.addObject("states", UFBrasil.values());
		mv.addObject("pagina", page);
		return mv;
	}
}
