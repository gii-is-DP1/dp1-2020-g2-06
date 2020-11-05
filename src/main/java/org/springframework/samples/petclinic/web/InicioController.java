package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
	
	@Autowired
	NoticiaService noticiasService;
	
	@GetMapping("/")
	public String inicio(ModelMap model) {
		model.addAttribute("noticias", noticiasService.findAll());
		return "inicio";
	}
	
	

}
