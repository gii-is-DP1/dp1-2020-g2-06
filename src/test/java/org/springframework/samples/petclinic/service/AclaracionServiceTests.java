package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith (MockitoExtension.class)
public class AclaracionServiceTests {
				
		@Autowired
		private AclaracionService aclaracionService;
		
		@Autowired
		TutorService tutorService;
		
		@Autowired
		ProblemaService problemaService;
		
		
		@Test
		public void shouldFindAll() {
			assertThat(aclaracionService.findAll().size()).isGreaterThan(0);
		}
		@Test
		public void shouldInsertAclaracion() {
			Collection<Aclaracion> aclaraciones = this.aclaracionService.findAll();
			int found = aclaraciones.size();
			
			Aclaracion aclaracion = new Aclaracion();

			
			aclaracion.setProblema(problemaService.findById(0).get());
			aclaracion.setTutor(tutorService.findById(0).get());
			aclaracion.setTexto("Prueba");
			
			
			
			this.aclaracionService.save(aclaracion);
			aclaraciones = this.aclaracionService.findAll();
			
			assertThat(aclaraciones.size()).isEqualTo(found +1);
		
		}
		
		
		@Test
		public void shouldFindAclaracionesByTutorInitial() {
			Collection<Aclaracion> aclaraciones = this.aclaracionService.findAllByTutor(2);
			assertThat( aclaraciones.size()).isEqualTo(1);
		}
		@Test
		public void shouldFindNotAclaracionesByTutorInitial() {
			Collection<Aclaracion> aclaraciones = this.aclaracionService.findAllByTutor(1);
			assertThat( aclaraciones.size()).isEqualTo(0);
		}

		@Test
		public void shouldFindAclaracionesByProblemaInitial() {
			Collection<Aclaracion> aclaraciones = this.aclaracionService.findAllByProblema(2);
			assertThat( aclaraciones.size()).isEqualTo(1);
		}
		@Test
		public void shouldFindNotAclaracionesByProblemaInitial() {
			Collection<Aclaracion> aclaraciones = this.aclaracionService.findAllByProblema(1);
			assertThat( aclaraciones.size()).isEqualTo(0);
		}

	
		
		
}
