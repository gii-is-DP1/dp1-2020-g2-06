package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.samples.petclinic.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
public class TutorServiceMockedTests {

	@Mock
	private TutorRepository tutorRepositoryMocked;
	
	@Mock
	private ArticuloRepository articuloRepositoryMocked;
	
	protected TutorService tutorServiceMocked;
	
	protected ArticuloService articuloServiceMocked;

	@BeforeEach
	void setup() {
		tutorServiceMocked = new TutorService(tutorRepositoryMocked);
		articuloServiceMocked = new ArticuloService(articuloRepositoryMocked);
		
	}
	
	@BeforeEach
	void setupProblema() {
		articuloServiceMocked = new ArticuloService(articuloRepositoryMocked);
	}
	
	@Test
	void testCalculoProblemasTutor() throws Exception{
		
		Tutor t1 = Mockito.mock(Tutor.class);
		Tutor t2 = Mockito.mock(Tutor.class);
		
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(t1);
		autores.add(t2);
		Articulo p1 = Mockito.mock(Articulo.class);
		Mockito.when(p1.getAutores()).thenReturn(autores); 
		
		assertEquals(2, p1.getAutores().size());
		
	}
	
}
