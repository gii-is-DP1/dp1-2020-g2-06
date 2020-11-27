package org.springframework.samples.petclinic.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
	
	@Autowired
	AlumnoService alumnoService;
	
	@GetMapping("")
	public String listAlumnos(ModelMap model) {
		model.addAttribute("alumnos",alumnoService.findAll());
		return "/alumnos/alumnosList";
	}
	
	@GetMapping("/{id}")
	public String alumnoDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if(alumno.isPresent()) {
			model.addAttribute("alumno",alumno.get());
			return "alumnos/alumnoDetails";
		}
		else {
			model.addAttribute("message","El alumno que intenta visualizar no existe");
			return listAlumnos(model);
		}
		
	}

}
