package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	TutorService tutorService;
	
	@GetMapping("")
	public String listArticulos(ModelMap model) {
		model.addAttribute("articulos", articuloService.findAll());
		return "/articulos/articulosList";
	}

	@GetMapping("/{id}")
	public String articuloDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(articulo.isPresent()) {
			model.addAttribute("articulo", articulo.get());
			model.addAttribute("autores", articuloService.findArticulosByTutor(id));
			return "articulos/articuloDetails";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el articulo");
			return listArticulos(model);	
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editArticulo(@PathVariable("id") int id, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(articulo.isPresent()) {
			model.addAttribute("articulo", articulo.get());
			model.addAttribute("autores", tutorService.findAll());
			return "articulos/createOrUpdateArticuloForm";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el articulo que intenta borrar");
			return listArticulos(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editArticulo(@PathVariable("id") int id, @Valid Articulo modifiedArticulo, BindingResult binding, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(binding.hasErrors()) {
			return "articulos/createOrUpdateArticuloForm";
		}
		else {
			BeanUtils.copyProperties(modifiedArticulo, articulo.get(), "id", "fechaPublicacion");
			articuloService.save(articulo.get());
			model.addAttribute("message","El artículo se ha actualizado con éxito");
			return listArticulos(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteArticulo(@PathVariable("id") int id, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(articulo.isPresent()) {
			articuloService.delete(articulo.get());
			model.addAttribute("message", "El articulo se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar el articulo que intenta borrar");
		}
		return listArticulos(model);
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String,Object> model) {
		Articulo articulo = new Articulo();
		model.put("articulo",articulo);
		model.put("autores", tutorService.findAll());
		return "articulos/createOrUpdateArticuloForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Articulo articulo, BindingResult result) {
		if(result.hasErrors()) {
			return "tutores/createOrUpdateArticuloForm";
		}else {
			this.articuloService.save(articulo);
			return "redirect:/articulos";
		}
	}
	

}

