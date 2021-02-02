package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PublicacionService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foro")
public class PublicacionController {
	
	@Autowired
	private PublicacionService publicacionService;
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping()
	public String listPublicaciones(ModelMap modelMap) {
		Publicacion publicacion = new Publicacion();
		Collection<Publicacion> cp= publicacionService.findAll();
		modelMap.addAttribute("publicaciones",cp);
		modelMap.addAttribute("publicacion",publicacion);
		return "publicaciones/publicacionesList";
	}
	
	@PostMapping(value = "/new")
	public String postPublicacion(@Valid Publicacion publicacion,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("message","Mensaje inválido");
			model.addAttribute("publicacion",publicacion);
			return listPublicaciones(model);
		}
		else{
			publicacion.setAlumno(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			publicacion.setFecha(LocalDateTime.now());
			publicacionService.save(publicacion);
			model.addAttribute("message","Mensaje añadido con éxito");
			return listPublicaciones(model);
		}
		
		
	}
	
}
