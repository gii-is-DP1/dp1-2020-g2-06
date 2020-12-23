package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.util.Utils;
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
@RequestMapping("/noticias")
public class NoticiaController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images");
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("")
	public String listNoticias(ModelMap model) {
		model.addAttribute("noticias", noticiaService.findAll());
		return "/noticias/noticiasList";
	}
	
	@GetMapping("/{id}")
	public String noticiaDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(noticia.isPresent()) {
			model.addAttribute("noticia", noticia.get());
			return "noticias/noticiaDetails";
		}
		else {
			model.addAttribute("message", "No podemos encontrar la noticia");
			return listNoticias(model);
			
		}
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Noticia noticia = new Noticia();
		model.addAttribute("noticia", noticia);
		return  "noticias/createOrUpdateNoticiaForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Noticia noticia,ModelMap model, BindingResult result,@RequestParam("image") MultipartFile imagen) throws IOException {
		if (result.hasErrors() || imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("noticia", noticia);
			return  "noticias/createOrUpdateNoticiaForm";
		}
		else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			noticia.setImagen("resources/images/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			noticia.setFechaPublicacion(LocalDate.now());
			noticiaService.save(noticia);
			
			return "redirect:/noticias/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(noticia.isPresent()) {
			model.addAttribute("noticia", noticia.get());
			return "noticias/createOrUpdateNoticiaForm";
		}
		else {
			model.addAttribute("message", "No podemos encontrar la noticia que intenta editar");
			return listNoticias(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, @Valid Noticia modifiedNoticia, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(binding.hasErrors() || imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("noticia", noticia.get());
			return "noticias/createOrUpdateNoticiaForm";
		}
		else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				noticia.get().setImagen("resources/images/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
				fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			}
			BeanUtils.copyProperties(modifiedNoticia, noticia.get(), "id", "fechaPublicacion", "imagen");
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
