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
import org.springframework.samples.petclinic.model.Competicion;
import org.springframework.samples.petclinic.service.CompeticionService;
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
@RequestMapping("/competiciones")
public class CompeticionController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/competiciones");

	@Autowired
	CompeticionService competicionService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("")
	public String listCompeticiones(ModelMap model) {
		model.addAttribute("competiciones", competicionService.findAll());
		return "/competiciones/competicionList";
	}
	
	@GetMapping("/{id}")
	public String competicionDetails(@PathVariable("id") int id, ModelMap model) {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(competicion.isPresent()) {
			model.addAttribute("competicion", competicion.get());
			return "competiciones/competicionDetails";
		}else {
			model.addAttribute("message", "La competicion al que intenta acceder no existe");
			return listCompeticiones(model);
		}
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Competicion competicion = new Competicion();
		model.put("competicion", competicion);
		return "competiciones/createOrUpdateCompeticionForm"; 
	}
		
	@PostMapping("/new")
	public String processCreationForm(@Valid Competicion competicion,BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		if(result.hasErrors()|| imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty()) {
			model.clear();
			model.addAttribute("competicion", competicion);
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			competicion.setImagen("resources/images/competiciones/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			this.competicionService.save(competicion);
			return "redirect:/competiciones/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editCompeticion(@PathVariable("id") int id, ModelMap model) {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(competicion.isPresent()) {
			model.addAttribute("competicion", competicion.get());
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			model.addAttribute("message","No se encuentra la competicion seleccionada");
			return listCompeticiones(model);
			
		}
	}
	
		
	@PostMapping("/{id}/edit")
	public String editCompeticion(@PathVariable("id") int id, @Valid Competicion modifiedCompeticion, BindingResult binding, ModelMap model,@RequestParam("image") MultipartFile imagen) throws BeansException, IOException {
		Optional<Competicion> competicion = competicionService.findById(id);
		if(binding.hasErrors()|| imagen.getBytes().length/(1024*1024)>10) {
			model.clear();
			model.addAttribute("competicion", competicion.get());
			model.addAttribute("message",binding.getFieldError().getField());
			return "competiciones/createOrUpdateCompeticionForm";
		}else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = competicion.get().getImagen();
				competicion.get().setImagen("resources/images/competiciones/"  + Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,Utils.diferenciador(extensionImagen[extensionImagen.length-1]));
			}
			BeanUtils.copyProperties(modifiedCompeticion, competicion.get(), "id", "problemas","imagen");
			competicionService.save(competicion.get());
			model.addAttribute("message","Se ha actualizado la competicion seleccionada");
			return listCompeticiones(model);		
		}
	}
	

}
