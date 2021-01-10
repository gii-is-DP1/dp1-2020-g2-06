package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
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
@RequestMapping("/aclaraciones")
public class AclaracionController {

	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	private AclaracionService aclaracionService;
	
	@GetMapping("/{id}")
	public String aclaracionDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<Aclaracion> aclaracion = aclaracionService.findById(id);
		if(aclaracion.isPresent()) {
			model.addAttribute("aclaracion",aclaracion.get());
			return "aclaraciones/aclaracionDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar la aclaración que intenta visualizar");
			return null;
		}
		
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Aclaracion aclaracion, BindingResult result, ModelMap model, @RequestParam("idProblema") Integer idProblema) throws IOException {
		if(result.hasErrors()) {
			model.addAttribute("message",result.getFieldError().getField());
			return "problemas";
		} else {
			aclaracion.setProblema(problemaService.findById(idProblema).get());
			aclaracion.setTutor(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			this.aclaracionService.save(aclaracion);
			return "redirect:/problemas";
		}
	}
	
}
