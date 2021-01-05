package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aclaraciones")
public class AclaracionController {
	
	@Autowired
	private AclaracionService aclaracionService;
	private static final String VIEWS_ACLARACION_CREATE_OR_UPDATE_FORM = "aclaraciones/createOrUpdateAclaracionesForm";
	
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
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Aclaracion aclaracion = new Aclaracion();
		model.addAttribute("aclaraciones", aclaracion);
		return VIEWS_ACLARACION_CREATE_OR_UPDATE_FORM;
	}


	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Aclaracion aclaracion, BindingResult result,ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("aclaracion", aclaracion);
			return VIEWS_ACLARACION_CREATE_OR_UPDATE_FORM;
		}
		else {

			aclaracionService.save(aclaracion);
			
			return "redirect:/aclaraciones/";
		}
	}
}
