package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/envios")
public class EnvioController {
	
	private final Path rootCodes = Paths.get("codes");
	
	@Autowired
	private EnvioService envioService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private ProblemaService problemaService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private JudgeService judgeService;
	
	
	@GetMapping("/{id}")
	public String envioDetails(@PathVariable("id") int id, ModelMap model) throws IOException {
		Optional<Envio> envio = envioService.findById(id);
		if(envio.isPresent()) {
			model.addAttribute("comentarioNuevo", new Comentario());
			model.addAttribute("comentarios", envio.get().getListaComentarios());
			model.addAttribute("envio",envio.get());
			model.addAttribute("codigo",envio.get().getCodigoString());
			return "envios/envioDetails";
		}
		else {
			model.addAttribute("message","No podemos encontrar el envío que intenta visualizar");
			return null;
		}
		
	}
	
	@PostMapping("/send/{problema}")
	public String envioSend(@PathVariable("problema") int problema, @RequestParam("archivo") MultipartFile archivo, ModelMap model) throws IOException, InterruptedException {
		if(!Utils.authLoggedIn().equals("alumno")) {
			model.addAttribute("message","Sólo los alumnos pueden realizar envíos");
			return null;  ///redirect al problema
		}
		else if(archivo.getBytes().length/(1024*1024)>10){
			model.addAttribute("message","Archivo demasiado grande");
			return null;  ///redirect al problema
		}
		else {
			String diferenciador = "";
			String filename = archivo.getOriginalFilename();
			String extension = "";
			if(filename.endsWith("java")) {
				extension = "java";
				diferenciador = Utils.diferenciador(extension);
			}
			else if(filename.endsWith("c")) {
				extension = "c";
				diferenciador = Utils.diferenciador(extension);
		}
			else if(filename.endsWith("cpp")) {
				extension = "cpp";
				diferenciador = Utils.diferenciador(extension);
			}
			else {
				model.addAttribute("message","Tipo de archivo incorrecto");
				return null; //redirect al problema
			}
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Envio e = new Envio();
			fileService.saveFile(archivo, rootCodes, diferenciador);
			fileService.saveFile(archivo, rootCodes, filename);
			
			e.setAlumno(alumnoService.findByEmail(email).get());	
			e.setFecha(LocalDateTime.now());
			e.setProblema(problemaService.findById(problema).get());
			e.setSeason(Utils.getActualSeason());
			e.setSeasonYear(Utils.getActualYearofSeason());
			e.setCodigoPath(rootCodes + "/" + diferenciador);
			Integer idJudge;
			String entryPoint = filename.substring(0, filename.length()-5);
			
			idJudge = judgeService.addSubmission(2, rootCodes + "/" + filename, extension, entryPoint, problemaService.findById(problema).get().getIdJudge());
			e.setIdJudge(idJudge);
			fileService.delete(Paths.get(rootCodes + "/" + filename));
			String veredict = judgeService.judge(2,idJudge);
			e.setResolucion(veredict);
			envioService.save(e);
			return "redirect:/envios/"+e.getId();
		}
	}

	
}
