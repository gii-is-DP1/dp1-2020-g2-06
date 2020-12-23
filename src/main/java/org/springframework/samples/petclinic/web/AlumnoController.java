package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
	
	private static final String VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM = "alumnos/createOrUpdateAlumnoForm";
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/alumnos");
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	FileService fileService;
	
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
			Collection<Problema> problemasResueltos = alumnoService.problemasResueltos(id);
			Collection<Problema> problemasResueltosYear = alumnoService.problemasResueltosThisYear(id);
			Collection<Problema> problemasResueltosSeason = alumnoService.problemasResueltosThisSeason(id);
			model.addAttribute("problemasresueltos",problemasResueltos);
			model.addAttribute("puntostotales",problemasResueltos.stream().mapToInt(x->x.getPuntuacion()).sum());
			model.addAttribute("puntosanuales",problemasResueltosYear.stream().mapToInt(x->x.getPuntuacion()).sum());
			model.addAttribute("puntostemporada",problemasResueltosSeason.stream().mapToInt(x->x.getPuntuacion()).sum());
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
	public String processCreationForm(@Valid Alumno alumno,ModelMap model, BindingResult result,@RequestParam("image") MultipartFile imagen) throws IOException {
		if (result.hasErrors() || imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("alumno", alumno);
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			alumno.setImagen("resources/images/alumnos/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
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
	public String editAlumno(@PathVariable("id") int id, @Valid Alumno modifiedAlumno, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("alumno", alumno.get());
			model.addAttribute("message",binding.getFieldError().getField());
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				alumno.get().setImagen("resources/images/alumnos/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
				fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			}
			BeanUtils.copyProperties(modifiedAlumno, alumno.get(), "id","imagen");
			alumnoService.save(alumno.get());
			model.addAttribute("message","Alumno actualizado con éxito");
			return listAlumnos(model);
		}
		
	}
	
	

}
