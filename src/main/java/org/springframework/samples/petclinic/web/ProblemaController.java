package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.ProblemaService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/problemas")
public class ProblemaController {

private static final String VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM = "problemas/createOrUpdateProblemaForm";
private final Path rootZip = Paths.get("uploads");
private final Path rootImage = Paths.get("src/main/resources/static/resources/images/problemas");
	
	@Autowired
	private ProblemaService problemaService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private EnvioService envioService;
	
	@Autowired
	private JudgeService judgeService;
	
	@Autowired
	private CreadorService creadorService;
	
	@GetMapping()
	public String listProblemas(ModelMap modelMap) {
		Collection<Problema> cp= problemaService.ProblemasVigentes();
		modelMap.addAttribute("problemasVigentes",cp);
		modelMap.addAttribute("temporada",Utils.getActualSeason().getNombre().toUpperCase());
		modelMap.addAttribute("temporadaYear",Utils.getActualYearofSeason());
		return "problemas/problemasList";
	}
	
	@GetMapping("/{id}")
	public String problemaDetails(@PathVariable("id") int id,ModelMap model) throws IOException {
		Optional<Problema> problema = problemaService.findById(id);
		Map<String, Long> resoluciones = envioService.resolucionProblema(id);
		Integer conseguidos = envioService.alumnosAC(id);
		if(problema.isPresent()) {
			if(problema.get().getCreador().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
				model.addAttribute("me",true);
			}else {
				model.addAttribute("me",false);
			}
			if(problema.get().isVigente()) {
				model.addAttribute("editarTrue",1);
			}
			if(problema.get().getSeasonYear().compareTo(Utils.getActualYearofSeason())>0 || (problema.get().getSeasonYear().compareTo(Utils.getActualYearofSeason())==0 && problema.get().getSeason().getId().compareTo(Utils.getActualSeason().getId())>0)) {
				model.addAttribute("message", "No puedes visualizar este problema");
				log.warn("Un usuario esta intentado realizar un envio de un problema, no en vigencia aun, con sesion ");
				return listProblemas(model);
			}
			model.addAttribute("aclaracion", new Aclaracion());
			model.addAttribute("problema", problema.get());
			model.addAttribute("resoluciones",resoluciones);
			model.addAttribute("conseguidos", conseguidos);
			model.addAttribute("totalEnvios",resoluciones.entrySet().stream().mapToLong(x->x.getValue()).sum());
			model.addAttribute("preguntaTutor", new PreguntaTutor());
			log.info("Se ha utilizado la libreria javascript 'morris.js'");
			return "problemas/problemaDetails";
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta visualizar");
			return listProblemas(model);
			
		}
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model, HttpServletRequest request) {
		if(!Utils.authLoggedIn().equals("creador")) {
			model.addAttribute("message","Para crear un problema debes estar registrado como creador");
			log.warn("Un usuario ha intentando crear un problema sin los permisos necesarios, con sesion: "+request.getSession());
			return listProblemas(model);
		}
		Problema problema = new Problema();
		model.addAttribute("problema", problema);
		return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Problema problema, BindingResult result,ModelMap model,@RequestParam("zipo") MultipartFile zip,@RequestParam("image") MultipartFile imagen) throws IOException{

		
			if (result.hasErrors() || zip.getBytes().length/(1024*1024)>40 || imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty() || zip.isEmpty()) {
				model.clear();
				model.addAttribute("problema", problema);
				List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
				if(imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10) {
					errores.add("La imagen debe tener un tamaño inferior a 10MB");
				}
				model.addAttribute("message", errores);
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String namezip = Utils.diferenciador("zip");
				fileService.saveFile(zip,rootZip,namezip);
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				problema.setImagen("resources/images/problemas/"  + name);
				fileService.saveFile(imagen,rootImage,name);
				
				Integer idJudge = judgeService.addProblem(2, rootZip + "/" + namezip).getProblemIds().get(0);
				problema.setIdJudge(idJudge);
				fileService.delete(rootZip.resolve(namezip));
				problema.setFechaPublicacion(LocalDate.now());
				problema.setCreador(creadorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
				problemaService.saveProblema(problema);
				model.addAttribute("message","Problema creado con exito");
				return listProblemas(model);
			}
		
	}
	
	@GetMapping("/{id}/edit")
	public String editProblema(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			if(!problema.get().getCreador().getId().equals(creadorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId()) && !Utils.authLoggedIn().equals("administrador")) {
				model.addAttribute("message","No puedes editar problemas de otros creadores");
				log.warn("Un usuario ha intentando editar un problema sin los permisos necesarios, con sesion: "+request.getSession());
				return listProblemas(model);
			}
			model.addAttribute("problema", problema.get());
			return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta editar");
			return listProblemas(model);
			
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editProblemas(@PathVariable("id") int id, @Valid Problema modifiedProblema, BindingResult binding, HttpServletRequest request, ModelMap model,@RequestParam("zipo") MultipartFile zip,@RequestParam("image") MultipartFile imagen) throws IOException {
			Optional<Problema> problema = problemaService.findById(id);
			if(!problema.get().getCreador().equals(creadorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get())) {
				model.addAttribute("message","No puedes editar problemas de otros creadores");
				log.warn("Un usuario ha intentando editar un problema sin los permisos necesarios, con sesion: "+request.getSession());
				return listProblemas(model);
			}
			if(binding.hasErrors() || zip.getBytes().length/(1024*1024)>20 || imagen.getBytes().length/(1024*1024)>10) {
				model.clear();
				model.addAttribute("problema", problema.get());
				List<String> errores = binding.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
				if(imagen.isEmpty() || imagen.getBytes().length/(1024*1024)>10) {
					errores.add("La imagen debe tener un tamaño inferior a 10MB");
				}
				model.addAttribute("message", errores);
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				if(!imagen.isEmpty()) {
					String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
					String aux = problema.get().getImagen();
					String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
					problema.get().setImagen("resources/images/problemas/"  + name);
					fileService.delete(Paths.get("src/main/resources/static/" + aux));
					fileService.saveFile(imagen,rootImage,name);
				}
				
				BeanUtils.copyProperties(modifiedProblema, problema.get(), "id","zip","imagen","fechaPublicacion","creador");
				
				problemaService.saveProblema(problema.get());
				model.addAttribute("message","Problema actualizado con éxito");
				return listProblemas(model);
			}
		
		
	}
	
	
}
