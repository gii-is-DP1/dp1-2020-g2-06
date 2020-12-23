package org.springframework.samples.petclinic.web;


import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tutores")
public class TutorController {

	@Autowired
	TutorService tutorService;
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	NoticiaService noticiaService;
	
	
	@GetMapping("")
	public String listTutores(ModelMap model) {
		model.addAttribute("tutores", tutorService.findAll());
		return "/tutores/tutoresList";
	}
	
	
	@GetMapping("/{id}/edit")
	public String editTutor(@PathVariable("id") int id, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(tutor.isPresent()) {
			model.addAttribute("tutor", tutor.get());
			return"tutores/createOrUpdateTutorForm";
		}else {
			model.addAttribute("message","No se encuentra el tutor seleccionado");
			return listTutores(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, @Valid Tutor modifiedTutor, BindingResult binding, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		if(binding.hasErrors()) {
			return "tutores/createOrUpdateTutorForm";
		}else {
			BeanUtils.copyProperties(modifiedTutor, tutor.get(), "id");
			tutorService.save(tutor.get());
			model.addAttribute("message","Tutor actualizado con exito");
			return listTutores(model);
		}
	}
	
	@GetMapping("/{id}")
	public String tutorDetails(@PathVariable("id") int id,@RequestParam(name="page-art", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Optional<Tutor> tutor = tutorService.findById(id);
		Integer pa = pagea;
		Integer pn = pagen;
		Pageable pageableA = PageRequest.of(pagea-1, 3, Sort.by("fecha_publicacion"));
		Pageable pageableN = PageRequest.of(pagen-1, 1, Sort.by("fecha_publicacion"));
		if(tutor.isPresent()) {
			model.addAttribute("tutor", tutor.get());
			model.addAttribute("noticiasTutor", noticiaService.findNoticiasByTutorPage(id, pageableN).getContent());
			model.addAttribute("articulosTutor", articuloService.findArticulosByTutorPage(id, pageableA).getContent());
			model.addAttribute("pagenotactual", pn);
			model.addAttribute("pageartactual", pa);
			model.addAttribute("npnoticia", pn+1);
			model.addAttribute("ppnoticia", pn-1);
			model.addAttribute("nparticulo", pa+1);
			model.addAttribute("pparticulo", pa-1);
			model.addAttribute("esUltimaPaginaArticulo", articuloService.findArticulosByTutorPage(id, pageableA).isLast());
			model.addAttribute("esPrimeraPaginaArticulo", articuloService.findArticulosByTutorPage(id, pageableA).isFirst());
			model.addAttribute("esUltimaPaginaNoticia", noticiaService.findNoticiasByTutorPage(id, pageableN).isLast());
			model.addAttribute("esPrimeraPaginaNoticia", noticiaService.findNoticiasByTutorPage(id, pageableN).isFirst());
			return "tutores/tutorDetails";
			
		}else {
			model.addAttribute("message", "El tutor al que intenta acceder no existe");
			return listTutores(model);
		}
		
	}
	
	@GetMapping("/new")
	public String initCreationForm(Map<String, Object> model) {
		Tutor tutor = new Tutor();
		model.put("tutor", tutor);
		return "tutores/createOrUpdateTutorForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Tutor tutor, BindingResult result) {
		if(result.hasErrors()) {
			return "tutores/createOrUpdateTutorForm";
		}else {
			this.tutorService.save(tutor);
			return "redirect:/tutores";
		}
	}
	
}
