package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TutorServiceTests {
	
	@Autowired
	TutorService tutorService;

	@Test
	public void shouldInsertTutor() {
		Collection<Tutor> tutores = this.tutorService.findAll();
		int found = tutores.size();

		Tutor tutor = new Tutor();
		tutor.setEmail("pepe@alum.us.es");
		tutor.setNombre("Pepe");
		tutor.setApellidos("Alvarez Toledo");
		tutor.setPass("Cuarentena123");
                
		this.tutorService.save(tutor);
		tutores = this.tutorService.findAll();
		
		assertThat(tutores.size()).isEqualTo(found + 1);
	}
	
	@Test
	void shouldUpdateOwner() {
		Tutor tutor = this.tutorService.finByEmail("alebarled@alum.us.es").get();
		String antiguoNombre = tutor.getNombre();
		String nuevoNombre = tutor.getNombre()+"x";
		
		tutor.setNombre(nuevoNombre);
		this.tutorService.save(tutor);
		
		tutor = this.tutorService.finByEmail("alebarled@alum.us.es").get();
		assertThat(tutor.getNombre()).isEqualTo(nuevoNombre);
		assertNotEquals(antiguoNombre, tutor.getNombre(), "Este nombre no coincide con el nombre actual del tutor");
	}
	
}
