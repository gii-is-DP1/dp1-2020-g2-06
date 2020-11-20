package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.service.NormaWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/normasWeb")
public class NormaWebController {
	
	@Autowired
	private NormaWebService normaWebService;
	
	@GetMapping()
	public String listNormasWeb(ModelMap modelMap) {
		String vista = "normasWeb/normasWebList";
		modelMap.addAttribute("normas_web",normaWebService.findAll());
		return vista;
	}
	
	@GetMapping("/{id}/edit")
	public String editNormaWeb(@PathVariable("id") int id, ModelMap model) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(normaWeb.isPresent()) {
			model.addAttribute("normas_web", normaWeb.get());
			return "normasWeb/createOrUpdateNormasWeb";
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
			return listNormasWeb(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNormasWeb(@PathVariable("id") int id, @Valid NormaWeb modifiedNormaWeb, BindingResult binding, ModelMap model) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(binding.hasErrors()) {
			return "normaWeb/createOrUpdateNormaWebForm";
		}
		else {
			BeanUtils.copyProperties(modifiedNormaWeb, normaWeb.get(), "id", "fechaPublicacion");
			normaWebService.save(normaWeb.get());
			model.addAttribute("message","NormaWeb actualizada con éxito");
			return listNormasWeb(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteNormasWeb(@PathVariable("id") int id, ModelMap model) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(normaWeb.isPresent()) {
			normaWebService.delete(normaWeb.get());
			model.addAttribute("message", "La norma Web se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
		}
		return listNormasWeb(model);
	}

}
