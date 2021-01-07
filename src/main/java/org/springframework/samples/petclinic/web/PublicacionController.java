package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.service.PublicacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tablon")
public class PublicacionController {
	
	@Autowired
	private PublicacionService publicacionService;
	
	@GetMapping()
	public String listPublicaciones(ModelMap modelMap) {
		Collection<Publicacion> cp= publicacionService.findAll();
		modelMap.addAttribute("publicaciones",cp);
		return "publicaciones/publicacionesList";
	}
	
}
