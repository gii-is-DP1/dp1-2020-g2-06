package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.service.NormaWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/normasWeb")
public class NormaWebController {
	
	@Autowired
	private NormaWebService normaWebService;
	
	@GetMapping()
	public String listadoNormasWeb(ModelMap modelMap) {
		String vista = "normasWeb/listadoNormasWeb";
		Iterable<NormaWeb> normasWeb= normaWebService.findAll();
		modelMap.addAttribute("normaWeb",normasWeb);
		return vista;
	}

}
