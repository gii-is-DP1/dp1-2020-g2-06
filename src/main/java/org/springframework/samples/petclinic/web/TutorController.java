package org.springframework.samples.petclinic.web;


import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tutores")
public class TutorController {

	@Autowired
	TutorService tutorService;
	
	
	@GetMapping("")
	public String listTutores(ModelMap model) {
		model.addAttribute("tutores", tutorService.findAll());
		return "/tutores/tutoresList";
	}
	
	
	@GetMapping("/{id}/edit")
	public String editTutor(@PathVariable("id") int id, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(tutor.isPresent()) {
			model.addAttribute("tutor", tutor.get());
			return"tutores/createOrUpdateTutorForm";
		}else {
			model.addAttribute("message","No se encuentra el tutor seleccionado");
			return listTutores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, @Valid Tutor modifiedTutor, BindingResult binding, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(binding.hasErrors()) {
			return "tutores/createOrUpdateTutorForm";
		}else {
			BeanUtils.copyProperties(modifiedTutor, tutor.get(), "id");
			tutorService.save(tutor.get());
			model.addAttribute("message","Tutor actualizado con exito");
			return listTutores(model);
		}
	}
	
	@GetMapping("/{id}")
	public String tutorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(tutor.isPresent()) {
			model.addAttribute("tutor", tutor.get());
			model.addAttribute("noticiasTutor", tutorService.findTutorNoticias(id));
			model.addAttribute("articulosTutor", tutorService.findTutorArticulos(id));
			return "tutores/tutorDetails";
		}else {
			model.addAttribute("message", "El tutor al que intenta acceder no existe");
			return listTutores(model);
		}
		
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Tutor tutor = new Tutor();
		model.put("tutor", tutor);
		return "tutores/createOrUpdateTutorForm";
	}
	
	@PostMapping("/new")
	public String r(@Valid Tutor tutor, BindingResult result) {
		if(result.hasErrors()) {
			return "tutores/createOrUpdateTutorForm";
		}else {
			this.tutorService.save(tutor);
			return "redirect:/tutores";
		}
	}
	
}
