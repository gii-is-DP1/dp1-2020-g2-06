<<<<<<< HEAD
package org.springframework.samples.petclinic.web;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/creadores")
public class CreadorController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/creadores");

	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	AdministradorService administradorService;
	
	@Autowired
	CreadorService creadorService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	AuthService authService;
	
	@GetMapping("")
	public String listCreadores(ModelMap model) {
		model.addAttribute("creadores", creadorService.findAll());
		return "/creadores/creadoresList";
	}
	
	@GetMapping("/new")
	public String initCreationForm(ModelMap model, HttpServletRequest request) {
		if(!Utils.authLoggedIn().equals("administrador")) {
			model.addAttribute("message","Para crear un creador debes estar registrado como administrador");
			log.warn("Un usuario esta intentando crear un creador sin ser administrador, con sesion "+request.getSession());
			return listCreadores(model);
		}
		Creador creador = new Creador();
		model.put("creador", creador);
		return "creadores/createOrUpdateCreadorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Creador creador, BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		boolean emailExistente = Utils.CorreoExistente(creador.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return "creadores/createOrUpdateCreadorForm";
		}
		if(result.hasErrors()|| imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty()) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "creadores/createOrUpdateCreadorForm";
		}else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			creador.setImagen("resources/images/creadores/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			Utils.imageCrop("resources/images/creadores/"  + name, fileService);
			creador.setEnabled(true);
			this.creadorService.save(creador);
			this.authService.saveAuthoritiesCreador(creador.getEmail(), "creador");
			return "redirect:/creadores";
		}
	}
	
	@GetMapping("/{id}")
	public String creadorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			if(creador.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
				model.addAttribute("me",true);
			}else {
				model.addAttribute("me",false);
			}
			model.addAttribute("creador", creador.get());
			return "creadores/creadorDetails";
		}else {
			model.addAttribute("message", "El creador al que intenta acceder no existe");
			return listCreadores(model);
		}
		
	}
	
	@GetMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			if(!creadorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
				model.addAttribute("message","Solo puedes editar tu propio perfil");
				log.warn("Un usuario esta intentando modificar un creador sin los permisos adecuados, con sesion "+request.getSession());
				return listCreadores(model);
			}
			model.addAttribute("creador", creador.get());
			return"creadores/createOrUpdateCreadorForm";
		}else {
			model.addAttribute("message","No se encuentra el creador seleccionado");
			return listCreadores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, @Valid Creador modifiedCreador, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen, HttpServletRequest request) throws BeansException, IOException {
		Optional<Creador> creador = creadorService.findById(id);
		if(!creadorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("message","Solo puedes editar tu propio perfil");
			log.warn("Un usuario esta intentando modificar un creador sin los permisos adecuados, con sesion "+request.getSession());
			return listCreadores(model);
		}
		boolean emailExistente = Utils.CorreoExistente(modifiedCreador.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("creador", modifiedCreador);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			log.warn("Un usuario esta intentando crear una cuenta de creador con una email ya registrado");
			return "creadores/createOrUpdateCreadorForm";
		}
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "creadores/createOrUpdateCreadorForm";
		}else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = creador.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				creador.get().setImagen("resources/images/creadores/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
				Utils.imageCrop("resources/images/creadores/"  + name, fileService);
			}
			BeanUtils.copyProperties(modifiedCreador, creador.get(), "id","imagen");
			creadorService.save(creador.get());
			model.addAttribute("message","Creador actualizado con exito");
			return listCreadores(model);
		}
	}
	
}
=======
package org.springframework.samples.petclinic.web;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/creadores")
public class CreadorController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/creadores");

	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	AdministradorService administradorService;
	
	@Autowired
	CreadorService creadorService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	AuthService authService;
	
	@GetMapping("")
	public String listCreadores(ModelMap model) {
		model.addAttribute("creadores", creadorService.findAll());
		return "/creadores/creadoresList";
	}
	
	@GetMapping("/new")
	public String initCreationForm(ModelMap model, HttpServletRequest request) {
		if(!Utils.authLoggedIn().equals("administrador")) {
			model.addAttribute("message","Para crear un creador debes estar registrado como administrador");
			log.warn("Un usuario esta intentando crear un creador sin ser administrador, con sesion "+request.getSession());
			return listCreadores(model);
		}
		Creador creador = new Creador();
		model.put("creador", creador);
		return "creadores/createOrUpdateCreadorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Creador creador, BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		boolean emailExistente = Utils.CorreoExistente(creador.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			return "creadores/createOrUpdateCreadorForm";
		}
		if(result.hasErrors()|| imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty()) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "creadores/createOrUpdateCreadorForm";
		}else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			creador.setImagen("resources/images/creadores/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			Utils.imageCrop("resources/images/creadores/"  + name, fileService);
			creador.setEnabled(true);
			this.creadorService.save(creador);
			this.authService.saveAuthoritiesCreador(creador.getEmail(), "creador");
			return "redirect:/creadores";
		}
	}
	
	@GetMapping("/{id}")
	public String creadorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			if(creador.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()) || Utils.authLoggedIn().equals("administrador")) {
				model.addAttribute("me",true);
			}else {
				model.addAttribute("me",false);
			}
			model.addAttribute("creador", creador.get());
			return "creadores/creadorDetails";
		}else {
			model.addAttribute("message", "El creador al que intenta acceder no existe");
			return listCreadores(model);
		}
		
	}
	
	@GetMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			if(!creadorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && !Utils.authLoggedIn().equals("administrador")) {
				model.addAttribute("message","Solo puedes editar tu propio perfil");
				log.warn("Un usuario esta intentando modificar sin los permisos adecuados, con sesion "+request.getSession());
				return listCreadores(model);
			}
			model.addAttribute("creador", creador.get());
			return"creadores/createOrUpdateCreadorForm";
		}else {
			model.addAttribute("message","No se encuentra el creador seleccionado");
			return listCreadores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, @Valid Creador modifiedCreador, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen, HttpServletRequest request) throws BeansException, IOException {
		Optional<Creador> creador = creadorService.findById(id);
		if(!creadorService.findById(id).get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && !Utils.authLoggedIn().equals("administrador")) {
			model.addAttribute("message","Solo puedes editar tu propio perfil");
			log.warn("Un usuario esta intentando modificar sin los permisos adecuados, con sesion "+request.getSession());
			return listCreadores(model);
		}
		boolean emailExistente = Utils.CorreoExistente(modifiedCreador.getEmail(),alumnoService,tutorService,creadorService,administradorService);
		if(emailExistente) {
			model.clear();
			model.addAttribute("creador", modifiedCreador);
			model.addAttribute("message", "Ya existe una cuenta con ese correo asociado");
			log.warn("Un usuario esta intentando crear una cuenta de creador con una email ya registrado");
			return "creadores/createOrUpdateCreadorForm";
		}
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("creador", creador);
			model.addAttribute("message", binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "creadores/createOrUpdateCreadorForm";
		}else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = creador.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				creador.get().setImagen("resources/images/creadores/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
				Utils.imageCrop("resources/images/creadores/"  + name, fileService);
			}
			BeanUtils.copyProperties(modifiedCreador, creador.get(), "id","imagen");
			creadorService.save(creador.get());
			model.addAttribute("message","Creador actualizado con exito");
			return listCreadores(model);
		}
	}
	
}
>>>>>>> branch 'juaostrub' of https://github.com/gii-is-DP1/dp1-2020-g2-06
