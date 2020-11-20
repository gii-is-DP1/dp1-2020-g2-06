package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noticias")
public class NoticiaController {
	
	@Autowired
	NoticiaService noticiaService;
	
	@GetMapping("")
	public String listNoticias(ModelMap model) {
		model.addAttribute("noticias", noticiaService.findAll());
		return "/noticias/noticiasList";
	}
	
	@GetMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(noticia.isPresent()) {
			model.addAttribute("noticia", noticia.get());
			return "noticias/createOrUpdateNoticiaForm";
		}
		else {
			model.addAttribute("message", "No podemos encontrar la noticia que intenta borrar");
			return listNoticias(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, @Valid Noticia modifiedNoticia, BindingResult binding, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(binding.hasErrors()) {
			return "noticias/createOrUpdateNoticiaForm";
		}
		else {
			BeanUtils.copyProperties(modifiedNoticia, noticia.get(), "id", "fechaPublicacion");
			noticiaService.save(noticia.get());
			model.addAttribute("message","Noticia actualizada con éxito");
			return listNoticias(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteNoticia(@PathVariable("id") int id, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(noticia.isPresent()) {
			noticiaService.delete(noticia.get());
			model.addAttribute("message", "La noticia se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la noticia que intenta borrar");
		}
		return listNoticias(model);
	}
	

}
