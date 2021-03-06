package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.service.NormaWebService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/normasWeb")
public class NormaWebController {
	
	private static final String VIEWS_NORMAWEB_CREATE_OR_UPDATE_FORM = "normasWeb/createOrUpdateNormaWebForm";
	
	@Autowired
	private NormaWebService normaWebService;
	
	@Autowired
	private TutorService tutorService;
	
	@GetMapping()
	public String listNormasWeb(ModelMap modelMap) {
		String vista = "normasWeb/normasWebList";
		modelMap.addAttribute("normas_web",normaWebService.findAll());
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		NormaWeb normas_web = new NormaWeb();
		model.addAttribute("normaWeb", normas_web);
		return VIEWS_NORMAWEB_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid NormaWeb normaWeb,BindingResult result,ModelMap model) {
		if (result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message", errores);
			return VIEWS_NORMAWEB_CREATE_OR_UPDATE_FORM;
		}
		else {
			normaWeb.setAutor(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			normaWebService.saveNormaWeb(normaWeb);
			return "redirect:/normasWeb/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editNormaWeb(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(!normaWebService.findById(id).get().getAutor().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("message","Solo puedes editar tus normas");
			log.warn("Un usuario esta intentando editar una norma sin tener los permisos necesarios, con sesion "+request.getSession());
			return listNormasWeb(model);
		}
		if(normaWeb.isPresent()) {
			model.addAttribute("normaWeb", normaWeb.get());
			return VIEWS_NORMAWEB_CREATE_OR_UPDATE_FORM;
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
			return listNormasWeb(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNormasWeb(@PathVariable("id") int id, @Valid NormaWeb modifiedNormaWeb, BindingResult binding, ModelMap model, HttpServletRequest request) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(!normaWebService.findById(id).get().getAutor().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("message","Solo puedes editar tus normas");
			log.warn("Un usuario esta intentando editar una norma sin tener los permisos necesarios, con sesion "+request.getSession());
			return listNormasWeb(model);
		}
		if(binding.hasErrors()) {
			List<String> errores = binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message", errores);
			return VIEWS_NORMAWEB_CREATE_OR_UPDATE_FORM;
		}
		else {
			BeanUtils.copyProperties(modifiedNormaWeb, normaWeb.get(), "id");
			normaWebService.saveNormaWeb(normaWeb.get());
			model.addAttribute("message","Norma Web actualizada con éxito");
			return listNormasWeb(model);
		}
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteNormasWeb(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
		Optional<NormaWeb> normaWeb = normaWebService.findById(id);
		if(!normaWebService.findById(id).get().getAutor().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			model.addAttribute("message","Solo puedes eliminar tus normas");
			log.warn("Un usuario esta intentando editar una norma sin tener los permisos necesarios, con sesion "+request.getSession());
			return listNormasWeb(model);
		}
		if(normaWeb.isPresent()) {
			normaWebService.delete(normaWeb.get());
			model.addAttribute("message", "La Norma Web se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la norma Web que intenta borrar");
		}
		return listNormasWeb(model);
	}

}
