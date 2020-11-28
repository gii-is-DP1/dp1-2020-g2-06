package org.springframework.samples.petclinic.web;


import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creadores")
public class CreadorController {

	@Autowired
	CreadorService creadorService;
	
	@GetMapping("")
	public String listCreadores(ModelMap model) {
		model.addAttribute("creadores", creadorService.findAll());
		return "/creadores/creadoresList";
	}
	
	
	@GetMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			model.addAttribute("creador", creador.get());
			return"creadores/createOrUpdateCreadorForm";
		}else {
			model.addAttribute("message","No se encuentra el creador seleccionado");
			return listCreadores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, @Valid Creador modifiedCreador, BindingResult binding, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(binding.hasErrors()) {
			return "creadores/createOrUpdateCreadorForm";
		}else {
			BeanUtils.copyProperties(modifiedCreador, creador.get(), "id");
			creadorService.save(creador.get());
			model.addAttribute("message","Creador actualizado con exito");
			return listCreadores(model);
		}
	}
	
	@GetMapping("/{id}")
	public String creadorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			model.addAttribute("creador", creador.get());
			return "creadores/creadorDetails";
		}else {
			model.addAttribute("message", "El creador al que intenta acceder no existe");
			return listCreadores(model);
		}
		
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Creador creador = new Creador();
		model.put("creador", creador);
		return "creadores/createOrUpdateCreadorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Creador creador, BindingResult result) {
		if(result.hasErrors()) {
			return "creadores/createOrUpdateCreadorForm";
		}else {
			this.creadorService.save(creador);
			return "redirect:/creadores";
		}
	}
	
}
