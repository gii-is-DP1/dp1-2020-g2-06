package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PublicacionServiceTests {

	@Autowired
	PublicacionService publicacionService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Test
	public void shouldFindAll() {
		assertThat(publicacionService.findAll().size()).isEqualTo(publicacionService.PublicacionCount());
	}
	
	@Test
	public void shouldFindPublicacionById() {
		Publicacion p = publicacionService.findById(0).get();
		assertThat(p.getAlumno()).isEqualTo(alumnoService.findById(0).get());
		assertThat(p.getFecha()).isEqualTo(LocalDateTime.of(2018, 11, 21, 11, 13, 13, 274000000));
		assertThat(p.getTexto()).isEqualTo("Estais listos para el Ada Byron?! Nos van a machacar!!!");
	}
	
	@Test
	public void shouldNotFindPublicacionById() {
		Assertions.assertThrows(Exception.class, () -> {
			Publicacion p = publicacionService.findById(99).get();
		});
	}
	
	@Test
	public void shouldInsertPublicacion() {
		Collection<Publicacion> puntuaciones = publicacionService.findAll();
		Integer nOld = puntuaciones.size();
		
		Publicacion publicacion = new Publicacion();
		publicacion.setAlumno(alumnoService.findById(0).get());
		publicacion.setFecha(LocalDateTime.now());
		publicacion.setTexto("Hola esto es una prueba, repito esto es una prueba un poco larga");
		
		publicacionService.save(publicacion);
		Integer nNew = publicacionService.findAll().size();
		assertNotEquals(nOld, nNew);
		assertThat(nNew).isEqualTo(nOld+1);
	}
	
	@Test
	public void shouldNotInsertPublicacion() {
		Publicacion publicacion = new Publicacion();
		publicacion.setAlumno(alumnoService.findById(0).get());
		publicacion.setFecha(LocalDateTime.now());
		publicacion.setTexto("");
		
		Assertions.assertThrows(ConstraintViolationException.class, ()-> {
			publicacionService.save(publicacion);
		});
			
	}
	
	@Test
	public void shouldUpdatePublicacion() {
		Publicacion publicacion = publicacionService.findById(0).get();
		String textoOld = publicacion.getTexto();
		String textoNew = publicacion.getTexto()+"!!!!";
		
		publicacion.setTexto(textoNew);
		assertThat(publicacion.getTexto()).isEqualTo(textoNew);
		assertNotEquals(textoOld, publicacion.getTexto());

	} 
	
	
	@Test
	public void shouldDeletePublicacion() {
		Collection<Publicacion> publicaciones = publicacionService.findAll();
		Integer nOld = publicaciones.size();
		
		publicacionService.delete(publicacionService.findById(0).get());
		publicaciones = publicacionService.findAll();
		Integer nNew = publicaciones.size();
		
		assertThat(nNew).isEqualTo(nOld-1);
		assertNotEquals(nOld, publicaciones);
		
	}
	
	@Test
	public void shouldNotDeletePublicacion() {
		Assertions.assertThrows(Exception.class, ()-> {
			publicacionService.delete(publicacionService.findById(99).get());
		});
			
		
	} 
	
}
