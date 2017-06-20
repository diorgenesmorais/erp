package com.dms.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ErrosController {

	@GetMapping("/404")
	public String pageNotFound() {
		return "404";
	}

	@PostMapping("/500")
	public String internalError() {
		return "500";
	}
}
