package org.springframework.samples.petclinic.web;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NoticiaService;
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
@RequestMapping("/tutores")
public class TutorController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/tutores");

	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	CreadorService creadorService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	AdministradorService administradorService;
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	PreguntaTutorService preguntaTutorService;
	
	
	@GetMapping("")
	public String listTutores(ModelMap model) {
		model.addAttribute("tutores", tutorService.findAll());
		return "/tutores/tutoresList";
	}
	
	@GetMapping("/new")
	public String initCreationForm(ModelMap model) {
		Tutor tutor = new Tutor();
		model.put("tutor", tutor);
		return "tutores/createOrUpdateTutorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Tutor tutor,BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		boolean emailExistente = Utils.CorreoExistente(tutor.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("tutor", tutor);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return "tutores/createOrUpdateTutorForm";
		}
		if(result.hasErrors()|| imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty()) {
			model.clear();
			model.addAttribute("tutor", tutor);
			model.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "tutores/createOrUpdateTutorForm";
		}else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			tutor.setImagen("resources/images/tutores/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			tutor.setEnabled(true);
			tutorService.save(tutor);
			authService.saveAuthoritiesTutor(tutor.getEmail(), "tutor");
			model.addAttribute("message", "Tutor creado con éxito");
			return listTutores(model);
		}
	}
	
	
	@GetMapping("/{id}/edit")
	public String editTutor(@PathVariable("id") int id, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(tutor.isPresent()) {
			if(!tutorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
				model.addAttribute("message","Solo puedes editar tu propio perfil");
				return listTutores(model);
			}
			model.addAttribute("tutor", tutor.get());
			return"tutores/createOrUpdateTutorForm";
		}else {
			model.addAttribute("message","No se encuentra el tutor seleccionado");
			return listTutores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editTutor(@PathVariable("id") int id, @Valid Tutor modifiedTutor, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Tutor> tutor = tutorService.findById(id);
		boolean emailExistente = Utils.CorreoExistente(modifiedTutor.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(!tutorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("message","Solo puedes editar tu propio perfil");
			return listTutores(model);
		}
		if(emailExistente) {
			model.clear();
			model.addAttribute("tutor", modifiedTutor);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return "tutores/createOrUpdateTutorForm";
		}
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("tutor", tutor.get());
			model.addAttribute("message", binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "tutores/createOrUpdateTutorForm";
		}else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = tutor.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				tutor.get().setImagen("resources/images/tutores/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
			}
			BeanUtils.copyProperties(modifiedTutor, tutor.get(), "id","imagen");
			tutorService.save(tutor.get());
			model.addAttribute("message","Tutor actualizado con exito");
			return listTutores(model);
		}
	}
	
	@GetMapping("/{id}")
	public String tutorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(tutor.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("me",true);
		}else {
			model.addAttribute("me",false);
		}

		if(tutor.isPresent()) {
			model.addAttribute("tutor", tutor.get());
			model.addAttribute("preguntasTutor",preguntaTutorService.findByProblemaNotAnswered());
			return "tutores/tutorDetails";
			
		}else {
			model.addAttribute("message", "El tutor al que intenta acceder no existe");
			return listTutores(model);
		}
		
	}
	
}
