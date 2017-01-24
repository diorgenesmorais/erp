package com.dms.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.controller.page.PageWrapper;
import com.dms.erp.model.Cliente;
import com.dms.erp.model.TipoPessoa;
import com.dms.erp.repository.Clientes;
import com.dms.erp.repository.filter.ClienteFilter;
import com.dms.erp.service.CadastroClienteService;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.useful.UFBrasil;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private CadastroClienteService clienteService;
	@Autowired
	private Clientes clientes;

	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastroCliente");
		mv.addObject("typesOfPeople", TipoPessoa.values());
		mv.addObject("states", UFBrasil.values());

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		try {
			this.clienteService.salvar(cliente);
		} catch (RegisteredAlreadyException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(ClienteFilter filter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/pesquisaCliente");
		PageWrapper<Cliente> page = new PageWrapper<>(clientes.filtrar(filter, pageable), httpServletRequest);
		mv.addObject("pagina", page);
		return mv;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		validNome(nome);
		return clientes.findByNomeStartingWithIgnoreCase(nome);
	}

	private void validNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException("O nome não pode ser vazio ou menor que 3 caracteres");
		}
	}
}
