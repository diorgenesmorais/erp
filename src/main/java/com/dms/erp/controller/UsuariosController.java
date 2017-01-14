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

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.Grupos;
import com.dms.erp.service.CadastroUsuarioService;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.erp.service.exception.SenhaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService usuarioService;
	@Autowired
	private Grupos grupos;

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/cadastroUsuario");
		mv.addObject("grupos", this.grupos.findAll());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}

		try {
			usuarioService.salvar(usuario);
		} catch (RegisteredAlreadyException e) {
			result.rejectValue("email", "1040", e.getMessage());
			return novo(usuario);
		} catch (SenhaUsuarioException e) {
			result.rejectValue("password", "1040", e.getMessage());
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");
	}
}
