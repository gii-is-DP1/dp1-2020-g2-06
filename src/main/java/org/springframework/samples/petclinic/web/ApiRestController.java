package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.PublicacionService;
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
	ArticuloService articuloService;
	
	@Autowired
	NoticiaService noticiaService;

	@Autowired
	CreadorService creadorService;
	
	@Autowired
	PublicacionService publicacionService;
	
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
	
	@GetMapping(value="/PageableCreadores",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Creador> getCreadores(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("nombre").descending());
		System.out.println( creadorService.findAllPageable(pageableA).getContent());
		return creadorService.findAllPageable(pageableA).getContent();
	}
	
	@GetMapping(value="/PageablePublicaciones",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Publicacion> getPublicaciones(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("id").descending());
		System.out.println(publicacionService.findAllPageable(pageableA).getContent().toString() + " listaaa");
		return publicacionService.findAllPageable(pageableA).getContent();
	}
}
