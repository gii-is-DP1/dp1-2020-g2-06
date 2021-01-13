package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/articulos");
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	private FileService fileService;
	
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
	
	@GetMapping("/new")
	public String initCreationForm(Map<String,Object> model) {
		Articulo articulo = new Articulo();
		model.put("articulo",articulo);
		model.put("autores", tutorService.findAll());
		return "articulos/createOrUpdateArticuloForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Articulo articulo,BindingResult result,ModelMap model, @RequestParam("image") MultipartFile imagen) throws IOException {
		if(result.hasErrors() || imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("articulo", articulo);
			model.addAttribute("autores", tutorService.findAll());
			model.addAttribute("message",result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			return "articulos/createOrUpdateArticuloForm";
		} else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			articulo.setImagen("resources/images/articulos/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			this.articuloService.save(articulo);
			return "redirect:/articulos";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editArticulo(@PathVariable("id") int id, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(!articulo.get().getAutores().contains(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get())) {
			model.addAttribute("message","Pide permiso a un autor para editar este artículo");
			return listArticulos(model);
		}
		if(articulo.isPresent()) {
			model.addAttribute("articulo", articulo.get());
			model.addAttribute("autores", tutorService.findAll());
			return "articulos/createOrUpdateArticuloForm";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el articulo que intenta editar");
			return listArticulos(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editArticulo(@PathVariable("id") int id, @Valid Articulo modifiedArticulo, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("articulo", articulo.get());
			model.addAttribute("autores", tutorService.findAll());
			model.addAttribute("message",binding.getFieldError().getField());
			return "articulos/createOrUpdateArticuloForm";
		}
		else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = articulo.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				articulo.get().setImagen("resources/images/articulos/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
			}
			BeanUtils.copyProperties(modifiedArticulo, articulo.get(), "id","imagen");
			articuloService.save(articulo.get());
			model.addAttribute("message","El artículo se ha actualizado con éxito");
			return listArticulos(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteArticulo(@PathVariable("id") int id, ModelMap model) {
		Optional<Articulo> articulo = articuloService.findById(id);
		if(!articulo.get().getAutores().contains(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get())) {
			model.addAttribute("message","Pide permiso a un autor para borrar este artículo");
			return listArticulos(model);
		}
		if(articulo.isPresent()) {
			articuloService.delete(articulo.get());
			model.addAttribute("message", "El articulo se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar el articulo que intenta borrar");
		}
		return listArticulos(model);
	}
	

}

