package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.PuntuacionProblema;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PuntuacionProblemaServiceTests {

	@Autowired
	PuntuacionProblemaService puntuacionProblemaService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	ProblemaService problemaService;
	
	
	@Test
	public void shouldFindAll() {
		assertThat(puntuacionProblemaService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindPuntuacionProblemaById() {
		PuntuacionProblema p = this.puntuacionProblemaService.findById(0).get();
		assertThat(p.getAlumno()).isEqualTo(alumnoService.findById(2).get());
		assertThat(p.getProblema()).isEqualTo(problemaService.findById(2).get());
		assertThat(p.getPuntuacion()).isEqualTo(3);
	}
	
	@Test
	public void shouldInsertPuntuacionProblema() {
		Collection<PuntuacionProblema> puntuaciones = puntuacionProblemaService.findAll();
		Integer nOld = puntuaciones.size();
		
		PuntuacionProblema puntuacion = new PuntuacionProblema();
		puntuacion.setAlumno(alumnoService.findById(0).get());
		puntuacion.setProblema(problemaService.findById(0).get());
		puntuacion.setPuntuacion(5);
		
		puntuacionProblemaService.save(puntuacion);
		Integer nNew = puntuacionProblemaService.findAll().size();
		assertNotEquals(nOld, nNew);
		assertThat(nNew).isEqualTo(nOld+1);
	}
	
	
	@Test
	public void shouldFindAllPuntuacionesByAlumno() {
		Integer puntuacionesMetodo = puntuacionProblemaService.findAllByAlumno(0).size();
		Collection<PuntuacionProblema> puntuaciones = puntuacionProblemaService.findAll();
		int puntuacionesFor = 0;
		for(PuntuacionProblema x : puntuaciones) {
			if(x.getAlumno().equals(alumnoService.findById(0).get())) {
				puntuacionesFor = puntuacionesFor+1;
			}
		}
		assertThat(puntuacionesMetodo).isEqualTo(puntuacionesFor);
		
	}
	

	
	@Test
	public void shouldFindAllPuntuacionesByProblema() {
		Integer puntuacionesMetodo = puntuacionProblemaService.findAllByProblema(2).size();
		Collection<PuntuacionProblema> puntuaciones = puntuacionProblemaService.findAll();
		Problema p = problemaService.findById(2).get();
		int puntuacionesFor = 0;
		for(PuntuacionProblema x : puntuaciones) {
			if(x.getAlumno().equals(p)) {
				puntuacionesFor = puntuacionesFor +1 ;
			}
		}
		assertThat(puntuacionesMetodo).isEqualTo(puntuacionesFor);
		
	}
	
}
