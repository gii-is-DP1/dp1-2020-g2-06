package org.springframework.samples.petclinic.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	AlumnoController alumnoController;
	
	@Autowired
	TutorController tutorController;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	CreadorController creadorController;
	
	@Autowired
	CreadorService creadorService;
	
	@GetMapping("")
	public String PerfilRedirection(ModelMap model) throws IOException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		String tipoPerfil = Utils.authLoggedIn();
		if(tipoPerfil.equals("alumno")) {
			return alumnoController.alumnoDetails(alumnoService.findByEmail(email).get().getId(), model);
		}else if(tipoPerfil.equals("tutor")){
			return tutorController.tutorDetails(tutorService.findByEmail(email).get().getId(), 1, 1, model);
		}else if(tipoPerfil.equals("creador")) {
			return creadorController.creadorDetails(creadorService.findByEmail(email).get().getId(), model);
		}else {
			return "/welcome";
		}
	}
	
}
