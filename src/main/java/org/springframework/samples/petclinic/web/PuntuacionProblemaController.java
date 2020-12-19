package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PuntuacionProblema;
import org.springframework.samples.petclinic.service.PuntuacionProblemaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/puntuacionesProblema")
public class PuntuacionProblemaController {
	
	@Autowired
	private PuntuacionProblemaService puntuacionProblemaService;
	
	@GetMapping("/{id}")
	public String puntuacionProblemaDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<PuntuacionProblema> puntuacionProblema = puntuacionProblemaService.findById(id);
		if(puntuacionProblema.isPresent()) {
			model.addAttribute("puntuacionProblema",puntuacionProblema.get());
			return "puntuacionesProblema/puntuacionProblemaDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar la puntuación que intenta visualizar");
			return null;
		}
		
	}

}
