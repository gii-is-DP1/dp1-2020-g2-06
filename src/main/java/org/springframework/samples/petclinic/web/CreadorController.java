package org.springframework.samples.petclinic.web;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
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
@RequestMapping("/creadores")
public class CreadorController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/creadores");

	@Autowired
	CreadorService creadorService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("")
	public String listCreadores(ModelMap model) {
		model.addAttribute("creadores", creadorService.findAll());
		return "/creadores/creadoresList";
	}
	
	
	@GetMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			model.addAttribute("creador", creador.get());
			return"creadores/createOrUpdateCreadorForm";
		}else {
			model.addAttribute("message","No se encuentra el creador seleccionado");
			return listCreadores(model);
		}
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Creador creador = new Creador();
		model.put("creador", creador);
		return "creadores/createOrUpdateCreadorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Creador creador, BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		if(result.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("creador", creador);
			return "creadores/createOrUpdateCreadorForm";
		}else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			creador.setImagen("resources/images/creadores/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			this.creadorService.save(creador);
			return "redirect:/creadores";
		}
	}
	
	@GetMapping("/{id}")
	public String creadorDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Creador> creador = creadorService.findById(id);
		if(creador.isPresent()) {
			model.addAttribute("creador", creador.get());
			return "creadores/creadorDetails";
		}else {
			model.addAttribute("message", "El creador al que intenta acceder no existe");
			return listCreadores(model);
		}
		
	}
	
	@PostMapping("/{id}/edit")
	public String editCreador(@PathVariable("id") int id, @Valid Creador modifiedCreador, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Creador> creador = creadorService.findById(id);
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("creador", creador);
			return "creadores/createOrUpdateCreadorForm";
		}else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				creador.get().setImagen("resources/images/creadores/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
				fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			}
			BeanUtils.copyProperties(modifiedCreador, creador.get(), "id");
			creadorService.save(creador.get());
			model.addAttribute("message","Creador actualizado con exito");
			return listCreadores(model);
		}
	}
	
}
