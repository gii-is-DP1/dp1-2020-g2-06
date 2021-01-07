package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.PreguntaTutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/preguntaTutor")
public class PreguntaTutorController {
	
	@Autowired
	private PreguntaTutorService preguntaTutorService;
	private static final String VIEWS_PREGUNTATUTOR_CREATE_OR_UPDATE_FORM = "preguntaTutor/createOrUpdatePreguntasTutorForm";
	
	@GetMapping("/{id}")
	public String preguntaTutorDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<PreguntaTutor> preguntaTutor= preguntaTutorService.findById(id);
		if(preguntaTutor.isPresent()) {
			model.addAttribute("preguntaTutor",preguntaTutor.get());
			return "preguntaTutor/preguntaTutorDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar la pregunta que intenta visualizar");
			return null;
		}
		
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		PreguntaTutor preguntaTutor = new PreguntaTutor();
		model.addAttribute("preguntaTutor", preguntaTutor);
		return VIEWS_PREGUNTATUTOR_CREATE_OR_UPDATE_FORM;
	}


	@PostMapping(value = "/new")
	public String processCreationForm(@Valid PreguntaTutor preguntaTutor, BindingResult result,ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("preguntaTutor", preguntaTutor);
			return VIEWS_PREGUNTATUTOR_CREATE_OR_UPDATE_FORM;
		}
		else {

			preguntaTutorService.save(preguntaTutor);
			
			return "redirect:/preguntaTutor/";
		}
	}
}
