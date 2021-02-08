package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProblemaServiceTests {

	@Autowired
	ProblemaService problemaService;
	
	@Autowired
	CreadorService creadorService;
	
	@Test
	void shouldFindAll() throws IOException {
		assertThat(problemaService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	void shouldFindProblemasVigentes() throws IOException {
		Integer numeroProblemasVigentes = problemaService.ProblemasVigentes().size();
		assertThat(numeroProblemasVigentes).isEqualTo(6);
	}
	
	@Test
	@Transactional
	void shouldFindProblemasNoVigentesPageable() throws IOException {
		Integer numeroProblemasNoVigentes = problemaService.ProblemasNoVigentePage(PageRequest.of(0, 1)).getSize();
		assertThat(numeroProblemasNoVigentes).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void shouldFindProblemasByTutor() throws IOException {
		Integer numeroProblemasTutor = problemaService.findAllByCreador(0).size();
		assertThat(numeroProblemasTutor).isEqualTo(11);
	}
	
	public void shouldInsertProblema() throws IOException {
		Integer size = this.problemaService.findAll().size();

		Problema problema = new Problema();
		problema.setName("La piscina olimpica");
		problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		problema.setCreador(creadorService.findById(0).get());
		problema.setPuntuacion(5);
		problema.setIdJudge(1);
		problema.setCasos_prueba("50 2 1");
		problema.setSalida_esperada("Si");
		problema.setImagen("https://www.imagendeprueba.com/2");
		
                
		this.problemaService.saveProblema(problema);	
		
		Collection<Problema> normasWeb2 = this.problemaService.findAll();
		
		assertThat(normasWeb2.size()).isEqualTo(size + 1);
	}
	
	@Test
	public void shouldThrowExceptionInsertingProblemaWithoutCasosPrueba() {
		Problema problema = new Problema();
		problema.setName("La piscina olimpica");
		problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		problema.setPuntuacion(5);
		problema.setCreador(creadorService.findById(0).get());
		problema.setIdJudge(1);
		problema.setSalida_esperada("Si");
		problema.setImagen("https://www.imagendeprueba.com/2");
		
		
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			this.problemaService.saveProblema(problema);	;
		});	
	}
	
	@Test
	@Transactional
	void shouldUpdateproblema() throws IOException {
		Problema problema = this.problemaService.findById(0).get();
		String oldDescripcion = problema.getDescripcion();
		String newDescripcion = oldDescripcion + "X";

		problema.setDescripcion(newDescripcion);
		this.problemaService.saveProblema(problema);

		// retrieving new name from database
		problema = this.problemaService.findById(0).get();
		assertThat(problema.getDescripcion()).isEqualTo(newDescripcion);
	}

}
