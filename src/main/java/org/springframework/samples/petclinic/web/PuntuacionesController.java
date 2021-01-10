package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PuntuacionProblema;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.PuntuacionProblemaService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/puntuaciones")
public class PuntuacionesController {
	/*
	@Autowired
	PuntuacionProblemaService ppProblemaService;
	
	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid PuntuacionProblema puntuacionNueva, BindingResult result, ModelMap model, @RequestParam("idProblema") Integer idProblema) throws IOException {
		if(result.hasErrors()) {
			model.addAttribute("message",result.getFieldError().getField());
			return "/problemas/"+idProblema;
		} else {
			puntuacionNueva.setProblema(problemaService.findById(idProblema).get());
			puntuacionNueva.setAlumno(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			problemaService.findById(idProblema).get().getPuntuacionesProblema().add(puntuacionNueva);
			this.ppProblemaService.save(puntuacionNueva);
			return "redirect:/problemas/"+idProblema;
		}
	}
	*/
	
}
