package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith (MockitoExtension.class)
public class AlumnoServiceTests {

		@Mock
		private AlumnoRepository alumnoRepositoryMocked;
		
		private AlumnoService alumnoServiceMocked;
		
		@Test
		public void shouldFindAll() {
			assertThat(alumnoServiceMocked.findAll().size()).isGreaterThan(0);
		}
		@Test
		public void shouldFindAlumnoById() {
			Alumno alumno= this.alumnoServiceMocked.findById(0).get();
			assertThat(alumno.getId()).isEqualTo(0);
			assertThat(alumno.getNombre()).isEqualTo("Daniel");
			assertThat(alumno.getApellidos()).isEqualTo("Montes");
			assertThat(alumno.getEmail()).isEqualTo("rarmon@alum.us.es");
			assertThat(alumno.getImagen()).isEqualTo("resources/images/alumnos/20201223154714879157200.jpg");
			assertThat(alumno.getPass()).isEqualTo("octavel0ver");
			assertThat(alumno.getCompartir()).isEqualTo(true);
		}
		
		@Test
		public void shouldInsertAlumno() {
			Alumno alumno = new Alumno();
			alumno.setNombre("Carmen");
			alumno.setApellidos("Barra");
			alumno.setEmail("carbarmen@alum.us.es");
			alumno.setImagen("resources/images/alumnos/20201223154714879157200.jpg");
			alumno.setPass("pass1234");
			alumnoServiceMocked.save(alumno);
			Mockito.verify(alumnoServiceMocked).save(alumno);
			String email = alumnoServiceMocked.findById(3).get().getEmail();
			
			assertThat(alumno.getEmail()).isEqualTo(email);
		}
}
