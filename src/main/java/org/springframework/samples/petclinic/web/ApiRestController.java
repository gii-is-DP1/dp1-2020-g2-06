package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiRestController {
	
	final int pagsize = 10;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	NoticiaService noticiaService;
	
	@GetMapping(value="/articulos/bytutor/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Articulo> getArticulosByTutor(@PathVariable("id") int id,@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("fecha_publicacion").descending());
		return articuloService.findArticulosByTutorPage(id, pageableA).getContent();
	}
	
	@GetMapping(value="/noticias/bytutor/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Noticia> getNoticiasByTutor(@PathVariable("id") int id,@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("fecha_publicacion").descending());
		return noticiaService.findNoticiasByTutorPage(id, pageableA).getContent();
	}
	
	@GetMapping(value="/tutores",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tutor> getTutores(@RequestParam(name="page", defaultValue="1") int pagea, ModelMap model) {
		Pageable pageableT = PageRequest.of(pagea-1, 5, Sort.by("apellidos"));
		return tutorService.findTutorPage(pageableT).getContent();
	}

}
