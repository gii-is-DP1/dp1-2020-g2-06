package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;


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
	protected TutorService tutorService;

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Tutor> tutores = this.tutorService.findAll();
		int found = tutores.size();

		Tutor tutor = new Tutor();
		tutor.setEmail("pepe@alum.us.es");
		tutor.setNombre("Pepe");
		tutor.setApellidos("Alvarez Toledo");
		tutor.setPass("Cuarentena123");
                
		this.tutorService.save(tutor);
		assertThat(tutor.getEmail().length()!=0);
		
		assertThat(tutores.size()).isEqualTo(found + 1);
	}
}
