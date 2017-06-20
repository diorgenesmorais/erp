package com.dms.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dms.erp.controller.page.PageWrapper;
import com.dms.erp.model.Usuario;
import com.dms.erp.repository.Grupos;
import com.dms.erp.repository.Usuarios;
import com.dms.erp.repository.filter.UsuarioFilter;
import com.dms.erp.service.CadastroUsuarioService;
import com.dms.erp.service.StatusUsuario;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.erp.service.exception.SenhaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService usuarioService;
	@Autowired
	private Grupos grupos;
	@Autowired
	private Usuarios usuarios;

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

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter filter,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuarios/pesquisaUsuarios");
		mv.addObject("grupos", this.grupos.findAll());
		
		PageWrapper<Usuario> pageWrapper = new PageWrapper<>(this.usuarios.filtrar(filter, pageable),
				httpServletRequest);
		mv.addObject("pagina", pageWrapper);

		return mv;
	}

	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK) // a requisição precisa de uma resposta
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		usuarioService.alterarStatus(codigos, statusUsuario);
	}
}
