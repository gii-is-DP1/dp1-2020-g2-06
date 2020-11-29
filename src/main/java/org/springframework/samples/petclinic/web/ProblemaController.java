package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problemas")
public class ProblemaController {

private static final String VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM = "problemas/createOrUpdateProblemaForm";
	
	@Autowired
	private ProblemaService problemaService;
	
	@GetMapping()
	public String listProblemas(ModelMap modelMap) {
		String vista = "problemas/problemasList";
		modelMap.addAttribute("problema",problemaService.findAll());
		return vista;
	}
	
	@GetMapping("/{id}")
	public String problemaDetails(@PathVariable("id") int id,ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			model.addAttribute("problema", problema.get());
			return "problemas/problemaDetails";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta visualizar");
			return listProblemas(model);
			
		}
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Problema problema = new Problema();
		model.addAttribute("problema", problema);
		return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Problema problema, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating problema
			problemaService.saveProblema(problema);
			
			return "redirect:/problemas/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editProblema(@PathVariable("id") int id, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			model.addAttribute("problema", problema.get());
			return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta borrar");
			return listProblemas(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editProblemas(@PathVariable("id") int id, @Valid Problema modifiedProblema, BindingResult binding, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(binding.hasErrors()) {
			return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		else {
			BeanUtils.copyProperties(modifiedProblema, problema.get(), "id");
			problemaService.saveProblema(problema.get());
			model.addAttribute("message","Problema actualizado con éxito");
			return listProblemas(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteProblemas(@PathVariable("id") int id, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			problemaService.delete(problema.get());
			model.addAttribute("message", "La norma Web se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
		}
		return listProblemas(model);
	}
}
