package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProblemaServiceTests {

	@Autowired
	ProblemaService ProblemaService;
	
	@Test
	public void testCountWithInitialData() {
		int count = ProblemaService.ProblemaCount();
		assertEquals(count, 3);
	}
	
	@Test
	public void shouldInsertProblema() {
		Collection<Problema> normasWeb = this.ProblemaService.findAll();
		int found = normasWeb.size();

		Problema Problema = new Problema();
		Problema.setName("La piscina olimpica");
		Problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		Problema.setPuntuacion(5);
		Problema.setCasos_prueba("50 2 1");
		Problema.setSalida_esperada("Si");
		Problema.setImagen("https://www.imagendeprueba.com/2");
                
		this.ProblemaService.saveProblema(Problema);	
		
		Collection<Problema> normasWeb2 = this.ProblemaService.findAll();
		
		assertThat(normasWeb2.size()).isEqualTo(found + 1);
	}
	
	@Test
	public void shouldThrowExceptionInsertingProblemaWithoutCasosPrueba() {
		Problema Problema = new Problema();
		Problema.setName("La piscina olimpica");
		Problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		Problema.setPuntuacion(5);
		Problema.setSalida_esperada("Si");
		Problema.setImagen("https://www.imagendeprueba.com/2");
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			this.ProblemaService.saveProblema(Problema);
		});	
	}
	
	@Test
	@Transactional
	void shouldUpdateproblema() {
		Problema problema = this.ProblemaService.findById(0).get();
		String oldDescripcion = problema.getDescripcion();
		String newDescripcion = oldDescripcion + "X";

		problema.setDescripcion(newDescripcion);
		this.ProblemaService.saveProblema(problema);

		// retrieving new name from database
		problema = this.ProblemaService.findById(0).get();
		assertThat(problema.getDescripcion()).isEqualTo(newDescripcion);
	}
	
	@Test
	public void shouldDeleteproblema() {
		Collection<Problema> problemas = this.ProblemaService.findAll();
		int found = problemas.size();
                
		Problema problema = ProblemaService.findById(0).get();
		this.ProblemaService.delete(problema);
		
		Collection<Problema> problemas2 = this.ProblemaService.findAll();
		
		assertThat(problemas2.size()).isEqualTo(found - 1);
	}
}
