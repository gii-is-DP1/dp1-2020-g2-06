package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
	
	@Autowired
	private ComentarioService comentarioService;
	
	@GetMapping("/{id}")
	public String comentarioDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<Comentario> comentario = comentarioService.findById(id);
		if(comentario.isPresent()) {
			model.addAttribute("comentario",comentario.get());
			return "comentario/comentarioDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar el comentario que intenta visualizar");
			return null;
		}
		
	}

}
