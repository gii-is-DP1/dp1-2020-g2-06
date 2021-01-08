package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PreguntaTutorServiceTests {
	
	@Autowired
	PreguntaTutorService preguntaTutorService;
	
	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	TutorService tutorService;
	
	@Test
	public void shouldFindPreguntaById() {
		PreguntaTutor pregunta = this.preguntaTutorService.findById(2).get();
		
		
		assertThat(pregunta.getPregunta()).isEqualTo("¿Dónde está Fuente Palmera?");
		assertThat(pregunta.getRespuesta()).isEqualTo("Eso no es relevante para la resolución del problema.");
		assertThat(pregunta.getAlumno()).isEqualTo(this.alumnoService.findById(1).get());
		assertThat(pregunta.getProblema()).isEqualTo(this.problemaService.findById(1).get());
		assertThat(pregunta.getTutor()).isEqualTo(this.tutorService.findById(1).get());
	}
	@Test
	public void shouldFindAllPreguntaTutorInitial() {
		Collection<PreguntaTutor> preguntas = this.preguntaTutorService.findAll();
		
		assertThat( preguntas.size()).isEqualTo(3);
	}
	
	
	@Test
	public void shouldInsertPreguntaTutor() {
		Collection<PreguntaTutor> preguntas = this.preguntaTutorService.findAll();
		int found = preguntas.size();
		
		
		PreguntaTutor pregunta = new PreguntaTutor();
		pregunta.setTutor(null);
		pregunta.setAlumno(this.alumnoService.findById(0).get());
		pregunta.setProblema(this.problemaService.findById(0).get());
		pregunta.setPregunta("No entiendo la prueba, ¿me explica?");
		pregunta.setRespuesta(null);

		this.preguntaTutorService.save(pregunta);
		Collection<PreguntaTutor> preguntas2 = this.preguntaTutorService.findAll();
		
		assertThat(preguntas2.size()).isEqualTo(found +1);
	
	}
	@Test
	public void shouldFindPreguntaTutorByProblemaInitial() {
		Collection<PreguntaTutor> preguntas = this.preguntaTutorService.findByProblema(0);
		assertThat( preguntas.size()).isEqualTo(2);
	}

}
