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
				
		@Autowired
		private AlumnoService alumnoService;
		
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
			alumno.setImagen("https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Pentagram4.svg/1200px-Pentagram4.svg.png");
//			alumno.setPuntosAnual(0);
//			alumno.setPuntosTemporada(0);
//			alumno.setPuntosTotales(0);
			alumno.setPass("pass1··DD234");
			alumnoService.save(alumno);
			String email = alumnoService.findById(3).get().getEmail();
			
			assertThat(alumno.getEmail()).isEqualTo(email);

		}
		
		
}
