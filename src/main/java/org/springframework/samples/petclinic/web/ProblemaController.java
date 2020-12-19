package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.zipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/problemas")
public class ProblemaController {

private static final String VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM = "problemas/createOrUpdateProblemaForm";
	
	@Autowired
	private ProblemaService problemaService;
	
	@Autowired
	private zipService zipService;
	
	@GetMapping()
	public String listProblemas(ModelMap modelMap) {
		String vista = "problemas/problemasList";
		Collection<Problema> cp= problemaService.ProblemasVigentes();
		modelMap.addAttribute("problemasVigentes",cp);
		modelMap.addAttribute("problemasNoVigentes",problemaService.ProblemasNoVigentes(cp));
		return vista;
	}
	
	@GetMapping("/{id}")
	public String problemaDetails(@PathVariable("id") int id,ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		
		if(problema.isPresent()) {
			if(problema.get().isVigente()) {
				model.addAttribute("editarTrue",1);
			}
			model.addAttribute("problema", problema.get());
			model.addAttribute("puntuacionMedia", problemaService.valoracionMediaAlumnno(problema.get()));
			model.addAttribute("ultimosEnvios", problema.get().getEnvios());
			return "problemas/problemaDetails";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta visualizar");
			return listProblemas(model);
			
		}
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Problema problema = new Problema();
		model.addAttribute("problema", problema);
		return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Problema problema, BindingResult result,@RequestParam("zipo") MultipartFile zip){
		String message;
		try {
			if (result.hasErrors()) {
				System.out.println(result.getAllErrors());
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				zipService.save(zip);
				problema.setZip("uploads/" + zip.getOriginalFilename());
				problema.setFechaPublicacion(LocalDate.now());
				problemaService.saveProblema(problema);
				message = "Uploaded the files successfully: " + zip.getOriginalFilename();
				return "redirect:/problemas/";
			}
		} catch (MaxUploadSizeExceededException e) {
		      message = "Fail to upload files!";
		      return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editProblema(@PathVariable("id") int id, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			model.addAttribute("problema", problema.get());
			return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta editar");
			return listProblemas(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editProblemas(@PathVariable("id") int id, @Valid Problema modifiedProblema, BindingResult binding, ModelMap model,@RequestParam("zipo") MultipartFile zip) throws IOException {
		String message;
		try {
			Optional<Problema> problema = problemaService.findById(id);
			if(binding.hasErrors()) {
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				if(!zip.isEmpty()) {
					zipService.save(zip);
					problema.get().setZip("uploads/" + zip.getOriginalFilename());
				}
				BeanUtils.copyProperties(modifiedProblema, problema.get(), "id","zip");
				problemaService.saveProblema(problema.get());
				model.addAttribute("message","Problema actualizado con éxito");
				return listProblemas(model);
			}
		} catch (MaxUploadSizeExceededException e) {
		      message = "Fail to upload files!";
		      return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteProblemas(@PathVariable("id") int id, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			problemaService.delete(problema.get());
			model.addAttribute("message", "La norma Web se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
		}
		return listProblemas(model);
	}
}
