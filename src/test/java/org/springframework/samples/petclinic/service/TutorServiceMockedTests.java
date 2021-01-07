package org.springframework.samples.petclinic.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.samples.petclinic.repository.TutorRepository;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TutorServiceMockedTests {

	@Mock
	private TutorRepository tutorRepositoryMocked;
	
	@Mock
	private ArticuloRepository articuloRepositoryMocked;
	
	protected TutorService tutorServiceMocked;
	
	protected ArticuloService articuloServiceMocked;

	@BeforeEach
	void setupTutor() {
		tutorServiceMocked = new TutorService(tutorRepositoryMocked);
	}
	
	@BeforeEach
	void setupProblema() {
		articuloServiceMocked = new ArticuloService(articuloRepositoryMocked);
	}
	
	@Test
	void testCalculoProblemasTutor() throws Exception{
		
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(tutorServiceMocked.findById(0).get());
		autores.add(tutorServiceMocked.findById(1).get());
		Articulo p1 = Mockito.mock(Articulo.class);
		Mockito.when(p1.getAutores()).thenReturn(autores); 
		
		assertEquals(2, p1.getAutores().size());
		
	}
}
