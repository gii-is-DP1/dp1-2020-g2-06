package org.springframework.samples.petclinic.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administradores")
public class AdministradorController {

	@Autowired
	AdministradorService administradorService;
	
	
	@GetMapping("")
	public String listadministradores(ModelMap model) {
		return "/administradores/panelAdmin";
	}
	
}
