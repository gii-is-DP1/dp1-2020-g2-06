package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ComentarioServiceTests {
				
		@Autowired
		ComentarioService comentarioService;
		
		@Autowired
		AlumnoService alumnoService;
		
		@Autowired
		EnvioService envioService;
		
		/*Haciendo una llamada a la funcion findAll() de ComentarioService, y compruebo que haya comentarios, para lo cual hago un assertThat, que compruebe que el tamaño sea mayor que 0*/
		@Test
		public void shouldFindAll() {
			assertThat(comentarioService.findAll().size()).isGreaterThan(0);
		}
		
		/*Haciendo una llamada a la funcion findAll() de ComentarioService, tomo el tamaño de la lista, y tras hacer uso de la llamada save, compruebo que haya cambiado el tamaño*/
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

		/*Haciendo uso de la funcion getById del servicio, tomo un comentario, y compruebo que sus parámetros sean los correctos*/
		@Test
		public void shouldFindById() {
			Comentario c = comentarioService.findById(0).get();
			assertThat(c.getEnvio().getId()).isEqualTo(10);
			assertThat(c.getAlumno().getId()).isEqualTo(0);
			assertThat(c.getTexto()).isEqualTo("Muy buena resolución del ejercicio, muy simple y muy claro.");
		}
		
		/*Haciendo una llamada a la funcion findAllByEnvio() de ComentarioService, compruebo que haya la cantidad de comentarios adecuadas para ese envio*/
		@Test
		public void shouldFindAllByEnvio() {
			assertThat(comentarioService.findAllByEnvio(10).size()).isEqualTo(2);
		}
		
		/*Haciendo una llamada a la funcion findAllByEnvio() de ComentarioService, compruebo que haya la cantidad de comentarios adecuadas para ese alumno*/	
		@Test
		public void shouldFindAllByAlumno() {
			assertThat(comentarioService.findAllByAlumno(2).size()).isEqualTo(2);
		}
		
		
		
}
