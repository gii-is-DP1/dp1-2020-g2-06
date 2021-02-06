package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/confirmation")
public class ConfirmationController {
	
	@Autowired	
	AlumnoService alumnoService;
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Alumno alumno,BindingResult result,ModelMap model, HttpServletRequest request) throws IOException, AddressException, MessagingException {
		
		if (result.hasErrors() ) {
			model.clear();
			model.addAttribute("alumno", alumno);
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message", errores);
			return "/alumnos/verificationView";
		}
		else {
			alumno.setEnabled(true);
			alumnoService.save(alumno);
			
			return "/alumnos/verificationView";
		}
	}
	
	

}
