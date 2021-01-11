package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.PreguntaTutorService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
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
	TutorService tutorService;
	
	@Autowired
	CreadorService creadorService;
	
	@Autowired
	AdministradorService administradorService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	PreguntaTutorService preguntasTutorService;
	
	@GetMapping("")
	public String listAlumnos(ModelMap model) {
		model.addAttribute("alumnos",alumnoService.findAll());
		return "/alumnos/alumnosList";
	}
	
	@GetMapping("/{id}")
	public String alumnoDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if(alumno.isPresent()) {
			if(alumno.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
				model.addAttribute("me",true);
			}else {
				model.addAttribute("me",false);
			}
			model.addAttribute("alumno",alumno.get());
			Collection<Problema> problemasResueltos = alumnoService.problemasResueltos(id);
			Collection<Problema> problemasResueltosYear = alumnoService.problemasResueltosThisYear(id);
			Collection<Problema> problemasResueltosSeason = alumnoService.problemasResueltosThisSeason(id);
			model.addAttribute("problemasresueltos",problemasResueltos);
			model.addAttribute("puntostotales",problemasResueltos.stream().mapToInt(x->x.getPuntuacion()).sum());
			model.addAttribute("puntosanuales",problemasResueltosYear.stream().mapToInt(x->x.getPuntuacion()).sum());
			model.addAttribute("puntostemporada",problemasResueltosSeason.stream().mapToInt(x->x.getPuntuacion()).sum());
			model.addAttribute("preguntasTutorNoRespondidas",preguntasTutorService.findByAlumnoIdNoRespondidas(id));
			model.addAttribute("preguntasTutorRespondidas",preguntasTutorService.findByAlumnoIdRespondidas(id));
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
	public String processCreationForm(@Valid Alumno alumno,BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		boolean emailExistente = Utils.CorreoExistente(alumno.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("alumno", alumno);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		if (result.hasErrors() || imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10 ) {
			model.clear();
			model.addAttribute("alumno", alumno);
			model.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			alumno.setImagen("resources/images/alumnos/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			alumno.setEnabled(true);
			alumnoService.save(alumno);
			authService.saveAuthoritiesAlumno(alumno.getEmail(), "alumno");
			
			return "redirect:/";
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
		boolean emailExistente = Utils.CorreoExistente(modifiedAlumno.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("alumno", modifiedAlumno);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("alumno", alumno.get());
			model.addAttribute("message",binding.getFieldError().getField());
			return VIEWS_ALUMNO_CREATE_OR_UPDATE_FORM;
		}
		else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = alumno.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				alumno.get().setImagen("resources/images/alumnos/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
			}
			BeanUtils.copyProperties(modifiedAlumno, alumno.get(), "id","imagen");
			alumnoService.save(alumno.get());
			model.addAttribute("message","Alumno actualizado con éxito");
			return listAlumnos(model);
		}
		
	}
	
	

}
