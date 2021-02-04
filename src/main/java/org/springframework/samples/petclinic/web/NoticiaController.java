package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NoticiaService;
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
@RequestMapping(value= {"/noticias","/"})
public class NoticiaController {
	
	private final Path rootImage = Paths.get("src/main/resources/static/resources/images/noticias");
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("")
	public String listNoticias(ModelMap model) {
		model.addAttribute("noticias", noticiaService.findAll());
		model.addAttribute("ranking_total",alumnoService.rankingTotal());
		model.addAttribute("ranking_temp",alumnoService.rankingTemporada());
		model.addAttribute("ranking_anual",alumnoService.rankingAnual());
		model.addAttribute("temporada",Utils.getActualSeason().getNombre().toUpperCase());
		model.addAttribute("temporadaYear",Utils.getActualYearofSeason());
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
	
	@GetMapping("/new")
	public String initCreationForm(ModelMap model) {
		Noticia noticia = new Noticia();
		model.put("noticia", noticia);
		model.put("autores", tutorService.findAll());

		return  "noticias/createOrUpdateNoticiaForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid Noticia noticia,BindingResult result,ModelMap model,@RequestParam("image") MultipartFile imagen) throws IOException {
		if (result.hasErrors() || imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10 ) {
			model.clear();
			model.addAttribute("noticia", noticia);
			model.addAttribute("autores", tutorService.findAll());
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			if(imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10) {
				errores.add("La imagen debe tener un tamaño inferior a 10MB");
			}
			model.addAttribute("message", errores);
			return  "noticias/createOrUpdateNoticiaForm";
		}
		else {
			String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
			String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
			noticia.setImagen("resources/images/noticias/"  + name);
			fileService.saveFile(imagen,rootImage,name);
			noticia.setFechaPublicacion(LocalDate.now());
			noticiaService.save(noticia);
			return "redirect:/noticias/";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String editNoticia(@PathVariable("id") int id, ModelMap model) {
		Optional<Noticia> noticia = noticiaService.findById(id);
		if(noticia.isPresent()) {
			if(!noticia.get().getAutores().contains(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get())) {
				model.addAttribute("message","Pide permiso a un autor para editar esta noticia");
				return listNoticias(model);
			}
			model.addAttribute("noticia", noticia.get());
			model.addAttribute("autores", tutorService.findAll());
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
			model.addAttribute("autores", tutorService.findAll());
			List<String> errores = binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			if(imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10) {
				errores.add("La imagen debe tener un tamaño inferior a 10MB");
			}
			model.addAttribute("message", errores);
			return "noticias/createOrUpdateNoticiaForm";
		}
		else {
			if(!imagen.isEmpty()) {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String aux = noticia.get().getImagen();
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				noticia.get().setImagen("resources/images/noticias/"  + name);
				fileService.delete(Paths.get("src/main/resources/static/" + aux));
				fileService.saveFile(imagen,rootImage,name);
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
			if(!noticia.get().getAutores().contains(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get())) {
				model.addAttribute("message","Pide permiso a un autor para editar esta noticia");
				return listNoticias(model);
			}
			noticiaService.delete(noticia.get());
			model.addAttribute("message", "La noticia se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar la noticia que intenta borrar");
		}
		return listNoticias(model);
	}
	

}
