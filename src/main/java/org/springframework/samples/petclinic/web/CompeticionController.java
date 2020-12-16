package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Competicion;
import org.springframework.samples.petclinic.service.CompeticionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/competiciones")
public class CompeticionController {

	@Autowired
	CompeticionService competicionService;
	
	@GetMapping("")
	public String listCompeticiones(ModelMap model) {
		model.addAttribute("competiciones", competicionService.findAll());
		return "/competiciones/competicionList";
	}
	
	@GetMapping("/{id}")
	public String competicionDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(competicion.isPresent()) {
			model.addAttribute("competicion", competicion.get());
			return "competiciones/competicionDetails";
		}else {
			model.addAttribute("message", "La competicion al que intenta acceder no existe");
			return listCompeticiones(model);
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editCompeticion(@PathVariable("id") int id, ModelMap model) {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(competicion.isPresent()) {
			model.addAttribute("competicion", competicion.get());
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			model.addAttribute("message","No se encuentra la competicion seleccionada");
			return listCompeticiones(model);
			
		}
	}
	
		
	@PostMapping("/{id}/edit")
	public String editCompeticion(@PathVariable("id") int id, @Valid Competicion modifiedCompeticion, BindingResult binding, ModelMap model) {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(binding.hasErrors()) {
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			BeanUtils.copyProperties(modifiedCompeticion, competicion.get(), "id", "problemas");
			competicionService.save(competicion.get());
			model.addAttribute("message","Se ha actualizado la competicion seleccionada");
			return listCompeticiones(model);		
		}
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Competicion competicion = new Competicion();
		model.put("competicion", competicion);
		return "competiciones/createOrUpdateCompeticionForm"; 
	}
		
	@PostMapping("/new")
	public String processCreationForm(@Valid Competicion competicion, BindingResult result) {
		if(result.hasErrors()) {
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			this.competicionService.save(competicion);
			return "redirect/:competiciones";
		}
	}
	

}
