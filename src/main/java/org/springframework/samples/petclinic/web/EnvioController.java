package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/envios")
public class EnvioController {
	
	@Autowired
	private EnvioService envioService;
	
	@GetMapping("/{id}")
	public String envioDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<Envio> envio = envioService.findById(id);
		if(envio.isPresent()) {
			model.addAttribute("comentarios", envio.get().getListaComentarios());
			model.addAttribute("envio",envio.get());
			model.addAttribute("codigo",envio.get().getCodigoString());
			return "envios/envioDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar el envío que intenta visualizar");
			return null;
		}
		
	}

	
}
