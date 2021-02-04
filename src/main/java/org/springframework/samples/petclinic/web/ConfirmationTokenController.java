package org.springframework.samples.petclinic.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ConfirmationTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/access")
public class ConfirmationTokenController {

	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	
	@GetMapping("/control")
	public String listAlumnos(ModelMap model) {
		//Crear aqui el envio del token por correo
		return "/alumnos/alumnosList";
	}
	
	@PostMapping(value = "/confirmation/{token}")
	public String processCreationForm() throws IOException {
		//Crear el alumno igualando con clase token
		return "/alumnos/alumnosList";
	}

}
