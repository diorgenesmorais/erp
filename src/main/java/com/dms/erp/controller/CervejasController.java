package com.dms.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.controller.page.PageWrapper;
import com.dms.erp.dto.CervejaDTO;
import com.dms.erp.model.Cerveja;
import com.dms.erp.model.Origem;
import com.dms.erp.model.Sabor;
import com.dms.erp.repository.Cervejas;
import com.dms.erp.repository.Estilos;
import com.dms.erp.repository.filter.CervejaFilter;
import com.dms.erp.service.CadastroCervejaService;

@Controller
@RequestMapping({ "/cervejas" })
public class CervejasController {

	@Autowired
	private Estilos estilos;
	@Autowired
	private CadastroCervejaService cadastroCervejaService;

	@Autowired
	private Cervejas cervejas;

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

	/**
	 * Método utilizado para página de pesquisa.
	 * 
	 * @param cervejaFilter
	 *            objeto submetido
	 * @param result
	 *            Necessário para submissão de parâmetros via GET
	 * @param pageable
	 *            Uso de paginação na consulta (necessita da anotação
	 *            {@code EnableSpringDataWebSupport} na classe {@code WebConfig}
	 *            )
	 * @param httpServletRequest
	 *            Informações da requisição.
	 * @return {@code ModelAndView}
	 * @see com.dms.erp.config.WebConfig é necessário anotar com
	 *      {@code EnableSpringDataWebSupport} para usar paginação.
	 */
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cerveja/pesquisaCervejas");
		mv.addObject("estilos", this.estilos.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());

		PageWrapper<Cerveja> pageWrapper = new PageWrapper<>(this.cervejas.filtrar(cervejaFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", pageWrapper);

		return mv;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) {
		return cervejas.bySkuOrNome(skuOuNome);
	}
}