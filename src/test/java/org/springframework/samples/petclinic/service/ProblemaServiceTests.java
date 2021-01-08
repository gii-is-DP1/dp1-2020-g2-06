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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProblemaServiceTests {

	@Autowired
	ProblemaService ProblemaService;
	
	public void shouldInsertProblema() throws IOException {
		Collection<Problema> normasWeb = this.ProblemaService.findAll();
		int found = normasWeb.size();

		Problema problema = new Problema();
		problema.setName("La piscina olimpica");
		problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		problema.setPuntuacion(5);
		problema.setIdJudge(1);
		problema.setCasos_prueba("50 2 1");
		problema.setSalida_esperada("Si");
		problema.setImagen("https://www.imagendeprueba.com/2");
		
		Path path = Paths.get("/TestTxt/TestFile.txt");
		String name = "file.txt";
		String originalFileName = "TestFile.txt";
		String contentType = "text/plain";
		byte[] content = null;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		
		MultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);
		
		problema.setZip("uploads/" + file.getOriginalFilename());
                
		this.ProblemaService.saveProblema(problema);	
		
		Collection<Problema> normasWeb2 = this.ProblemaService.findAll();
		
		assertThat(normasWeb2.size()).isEqualTo(found + 1);
	}
	
	@Test
	public void shouldThrowExceptionInsertingProblemaWithoutCasosPrueba() {
		Problema problema = new Problema();
		problema.setName("La piscina olimpica");
		problema.setDescripcion("Una piscina olimica tiene 50 metros de largo...");
		problema.setPuntuacion(5);
		problema.setIdJudge(1);
		problema.setSalida_esperada("Si");
		problema.setImagen("https://www.imagendeprueba.com/2");
		
		Path path = Paths.get("/TestTxt/TestFile.txt");
		String name = "file.txt";
		String originalFileName = "TestFile.txt";
		String contentType = "text/plain";
		byte[] content = null;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		
		MultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);
		
		problema.setZip("uploads/" + file.getOriginalFilename());
		
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			this.ProblemaService.saveProblema(problema);	;
		});	
	}
	
	@Test
	@Transactional
	void shouldUpdateproblema() throws IOException {
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
