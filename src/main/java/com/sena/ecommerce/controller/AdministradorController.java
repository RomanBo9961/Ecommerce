package com.sena.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// define a la clase como tipo controlador
@Controller
@RequestMapping("/administrador")// solicitud de mapeo al directorio administrador

public class AdministradorController {

	@GetMapping("")
	public String home() {
		return "administrador/home";
	}
}
