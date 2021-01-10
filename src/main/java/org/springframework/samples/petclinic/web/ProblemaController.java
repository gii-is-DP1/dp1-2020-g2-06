package org.springframework.samples.petclinic.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.JudgeService;
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
	
	@GetMapping()
	public String listProblemas(ModelMap modelMap) {
		Collection<Problema> cp= problemaService.ProblemasVigentes();
		modelMap.addAttribute("problemasVigentes",cp);
		modelMap.addAttribute("problemasNoVigentes",problemaService.ProblemasNoVigentes(cp));
		return "problemas/problemasList";
	}
	
	@GetMapping("/{id}")
	public String problemaDetails(@PathVariable("id") int id,ModelMap model) throws IOException {
		Optional<Problema> problema = problemaService.findById(id);
		Map<String, Long> resoluciones = envioService.resolucionProblema(id);
		if(problema.isPresent()) {
			if(problema.get().isVigente()) {
				model.addAttribute("editarTrue",1);
			}
			model.addAttribute("problema", problema.get());
			model.addAttribute("ultimosEnvios", problema.get().getEnvios());
			model.addAttribute("resoluciones",resoluciones);
			model.addAttribute("totalEnvios",envioService.findAllByProblema(id).size());
			model.addAttribute("preguntaTutor", new PreguntaTutor());
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
	public String processCreationForm(@Valid Problema problema, BindingResult result,ModelMap model,@RequestParam("zipo") MultipartFile zip,@RequestParam("image") MultipartFile imagen) throws IOException{

		
			if (result.hasErrors() || zip.getBytes().length/(1024*1024)>40 || imagen.getBytes().length/(1024*1024)>10 || imagen.isEmpty() || zip.isEmpty()) {
				model.clear();
				model.addAttribute("problema", problema);
				model.addAttribute("message", result.getAllErrors());
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
				String namezip = Utils.diferenciador("zip");
				problema.setZip(rootZip + "/" + namezip);
				fileService.saveFile(zip,rootZip,namezip);
				String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
				problema.setImagen("resources/images/problemas/"  + name);
				fileService.saveFile(imagen,rootImage,name);
				
				Integer idJudge = judgeService.addProblem(2, rootZip + "/" + namezip).getProblemIds().get(0);
				problema.setIdJudge(idJudge);
				
				problema.setFechaPublicacion(LocalDate.now());
				problemaService.saveProblema(problema);
				String message = "Uploaded the files successfully: ";
				return "redirect:/problemas/";
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
	public String editProblemas(@PathVariable("id") int id, @Valid Problema modifiedProblema, BindingResult binding, ModelMap model,@RequestParam("zipo") MultipartFile zip,@RequestParam("image") MultipartFile imagen) throws IOException {
			System.out.println("hello");
			Optional<Problema> problema = problemaService.findById(id);
			if(binding.hasErrors() || zip.getBytes().length/(1024*1024)>20 || imagen.getBytes().length/(1024*1024)>10) {
				model.clear();
				model.addAttribute("problema", problema.get());
				model.addAttribute("message",binding.getFieldError().getField());
				return VIEWS_PROBLEMA_CREATE_OR_UPDATE_FORM;
			}
			else {
				if(!zip.isEmpty()) {
					String aux = problema.get().getZip();
					problema.get().setZip(rootZip + "/" + Utils.diferenciador("zip"));
					fileService.delete(Paths.get(aux));
					fileService.saveFile(zip,rootZip,Utils.diferenciador("zip"));
				}
				if(!imagen.isEmpty()) {
					String extensionImagen[] = imagen.getOriginalFilename().split("\\.");
					String aux = problema.get().getImagen();
					String name = Utils.diferenciador(extensionImagen[extensionImagen.length-1]);
					problema.get().setImagen("resources/images/problemas/"  + name);
					fileService.delete(Paths.get("src/main/resources/static/" + aux));
					fileService.saveFile(imagen,rootImage,name);
				}
				BeanUtils.copyProperties(modifiedProblema, problema.get(), "id","zip","imagen","fechaPublicacion");
				problemaService.saveProblema(problema.get());
				model.addAttribute("message","Problema actualizado con éxito");
				return listProblemas(model);
			}
		
		
	}
	
	@GetMapping("/{id}/delete")
	public String deleteProblemas(@PathVariable("id") int id, ModelMap model) {
		Optional<Problema> problema = problemaService.findById(id);
		if(problema.isPresent()) {
			problemaService.delete(problema.get());
			model.addAttribute("message", "El problema se ha borrado con exito");
		}
		else {
			model.addAttribute("message", "No podemos encontrar el problema que intenta borrar");
		}
		return listProblemas(model);
	}
}
