package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
	
	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private EnvioService envioService;
	
	@Autowired
	private AlumnoService alumnoService;
	

	@PostMapping("/new")
	public String processCreationForm(@Valid Comentario comentarioNuevo, BindingResult result, ModelMap model, @RequestParam("idEnvio") Integer idEnvio) throws IOException {
		if(result.hasErrors()) {
			model.addAttribute("message",result.getFieldError().getField());
			return "/envios/"+idEnvio;
		} else {
			comentarioNuevo.setEnvio(envioService.findById(idEnvio).get());
			comentarioNuevo.setAlumno(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			this.comentarioService.save(comentarioNuevo);
			return "redirect:/envios/"+idEnvio;
		}
	}
	
}