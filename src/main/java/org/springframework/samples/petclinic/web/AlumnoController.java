package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
	
	private static final String VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM = "alumnos/createOrUpdateAlumnoForm";
	
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
//			model.addAttribute("envios",alumno.get().getEnvios());
			return "alumnos/alumnoDetails";
		}
		else {
			model.addAttribute("message","El alumno que intenta visualizar no existe");
			return listAlumnos(model);
		}
		
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Alumno alumno = new Alumno();
		model.addAttribute("alumno", alumno);
		return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Alumno alumno, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			alumno.setPuntosAnual(0);
			alumno.setPuntosTemporada(0);
			alumno.setPuntosTotales(0);
			alumnoService.save(alumno);
			
			return "redirect:/alumnos/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editAlumno(@PathVariable("id") int id, ModelMap model) {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if(alumno.isPresent()) {
			model.addAttribute("alumno", alumno.get());
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			model.addAttribute("message", "El alumno que intenta editar no existe");
			return listAlumnos(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editAlumno(@PathVariable("id") int id, @Valid Alumno modifiedAlumno, BindingResult binding, ModelMap model) {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if(binding.hasErrors()) {
			model.addAttribute("message",binding.getFieldError().getField());
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			BeanUtils.copyProperties(modifiedAlumno, alumno.get(), "id");
			alumnoService.save(alumno.get());
			model.addAttribute("message","Alumno actualizado con éxito");
			return listAlumnos(model);
		}
		
	}
	
	

}
