package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith (MockitoExtension.class)
public class ComentarioServiceTests {
				
		@Autowired
		ComentarioService comentarioService;
		
		@Autowired
		AlumnoService alumnoService;
		
		@Autowired
		EnvioService envioService;
		
		
		@Test
		public void shouldFindAll() {
			assertThat(comentarioService.findAll().size()).isGreaterThan(0);
		}
		
		@Test
		public void shouldInsertComentario() {
			Collection<Comentario> comentarios = this.comentarioService.findAll();
			int found = comentarios.size();
			
			Comentario comentario = new Comentario();

			comentario.setAlumno(alumnoService.findById(0).get());
			comentario.setTexto("Muy buen test");
			comentario.setEnvio(envioService.findById(1).get());		
			
			
			this.comentarioService.save(comentario);
			comentarios = this.comentarioService.findAll();
			
			assertThat(comentarios.size()).isEqualTo(found +1);
		}
		
		@Test
		public void shouldFindById() {
			assertThat(comentarioService.findById(0));
		}
		
		@Test
		public void shouldFindAllByEnvio() {
			assertThat(comentarioService.findAllByEnvio(0));
		}
		
		@Test
		public void shouldFindAllByAlumno() {
			assertThat(comentarioService.findAllByAlumno(0));
		}
		
		
		
}
