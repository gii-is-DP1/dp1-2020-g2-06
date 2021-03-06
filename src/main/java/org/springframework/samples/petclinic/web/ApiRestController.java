package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.TutorService;

import org.springframework.samples.petclinic.service.PublicacionService;
import org.springframework.samples.petclinic.service.ProblemaService;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiRestController {
	
	final int pagsize = 7;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	ArticuloService articuloService;
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	EnvioService envioService;
	
	@Autowired
	PublicacionService publicacionService;

	@Autowired
	CreadorService creadorService;
	
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
		Pageable pageableT = PageRequest.of(pagea-1, pagsize, Sort.by("apellidos"));
		return tutorService.findTutorPage(pageableT).getContent();
	}

	@GetMapping(value="/problemasnovigentes",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Problema> getProblemasNoVigentes(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize);
		return problemaService.ProblemasNoVigentePage(pageableA).getContent();
	}
	
	@GetMapping(value="/PageableCreadores",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Creador> getCreadores(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("nombre").descending());
		System.out.println( creadorService.findAllPageable(pageableA).getContent());
		return creadorService.findAllPageable(pageableA).getContent();
	}
	
	@GetMapping(value="/envios/byproblema/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Envio> getEnviosByProblema(@PathVariable("id") int id,@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize,Sort.by("id").descending());
		return envioService.findAllByProblemaPage(pageableA,id).getContent();
	}
	
	@GetMapping(value="/envios/byalumno/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Envio> getEnviosByAlumno(@PathVariable("id") int id,@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize,Sort.by("id").descending());
		return envioService.findAllByAlumnoPage(pageableA,id).getContent();
	}
	
	@GetMapping(value="/noticias",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Noticia> getNoticias(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, 3,Sort.by("id").descending());
		return noticiaService.findAllPage(pageableA).getContent();
		
	}
	
	@GetMapping(value="/PageablePublicaciones",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Publicacion> getPublicaciones(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("id").descending());
		log.debug(publicacionService.findAllPageable(pageableA).getContent().toString() + "listaa");
		return publicacionService.findAllPageable(pageableA).getContent();
	}

	@GetMapping(value="/articulospage",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Articulo> getArticulos(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("fecha_publicacion").descending());
		return articuloService.findAllArticulosPage(pageableA).getContent();
	}
	
	@GetMapping(value="/alumnospage",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Alumno> getALumnos(@RequestParam(name="page", defaultValue="1") int pagea, @RequestParam(name="page-not", defaultValue="1") int pagen, ModelMap model) {
		Pageable pageableA = PageRequest.of(pagea-1, pagsize, Sort.by("id").descending());
		return alumnoService.findAllPage(pageableA).getContent();

	}
	

}
