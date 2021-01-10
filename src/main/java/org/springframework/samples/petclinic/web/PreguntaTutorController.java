package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PreguntaTutorService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/preguntatutor")
public class PreguntaTutorController {
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	private PreguntaTutorService preguntaTutorService;
	
	@Autowired
	ProblemaController problemaController;
	
	@Autowired
	TutorController tutorController;


	@PostMapping(value = "/new")
	public String processCreationForm(@Valid PreguntaTutor preguntaTutor, BindingResult result,ModelMap model,@RequestParam("idProblema") Integer idProblema) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("message",result.getFieldError().getField());
			return problemaController.problemaDetails(idProblema,model);
		}
		else {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println(alumnoService.findByEmail(email).get().getApellidos());
			preguntaTutor.setAlumno(alumnoService.findByEmail(email).get());
			preguntaTutor.setProblema(problemaService.findById(idProblema).get());
			preguntaTutorService.save(preguntaTutor);
			
			model.addAttribute("message","Pregunta enviada con éxito");
			return problemaController.problemaDetails(idProblema,model);
		}
	}
	
	@PostMapping(value = "/answer")
	public String answer(ModelMap model,@RequestParam("idTutor") Integer idTutor,@RequestParam("preguntaTutor") Integer idpreguntaTutor,@RequestParam("respuesta") String respuesta) throws IOException {
		System.out.println("hola");
		if (respuesta.isEmpty()) {
			model.addAttribute("message","La respuesta no puede estar vacía");
			return tutorController.tutorDetails(idTutor, 1, 1, model);
		}
		else {
			PreguntaTutor preguntaTutor = preguntaTutorService.findById(idpreguntaTutor).get();
			preguntaTutor.setTutor(tutorService.findById(idTutor).get());
			preguntaTutor.setRespuesta(respuesta);
			preguntaTutorService.save(preguntaTutor);
			
			model.addAttribute("message","Respuesta realizada con éxito");

			return tutorController.tutorDetails(idTutor, 1, 1, model);
		}
	}
}
