package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LogroServiceTests {

	@Autowired
	LogroService logroService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Test
	public void shouldFindAll() {
		assertThat(logroService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindById() {
		Logro logro = logroService.findById(0).get();
		assertThat(logro.getImagen()).isEqualTo("resources/images/logros/logroEnvio.jpg");
		assertThat(logro.getNombre()).isEqualTo("10 envios realizados");
	}
	
//	@Test
//	public void shouldLogrosAlumno() {
//		Alumno alumno = alumnoService.findById(0).get();
//		Collection<Logro> logros = new ArrayList<Logro>();
//		logros.add(logroService.obtenerLogroEnvio(alumno));
//		logros.add(logroService.obtenerLogroAccepted(alumno));
//		logros.add(logroService.obtenerLogroWrong(alumno));
//		
//		assertThat(logros.size()).isEqualTo(3);
//	}
	
	
}