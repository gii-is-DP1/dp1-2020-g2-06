package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.This;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class NoticiaServiceTests {
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	TutorService tutorService;
	
	@Test
	public void shouldInsertNoticia() {
		Collection<Noticia> noticias = this.noticiaService.findAll();
		int found = noticias.size();
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(tutorService.findById(0).get());
		Noticia noticia = new Noticia();
		noticia.setAutores(autores);
		noticia.setAutor(tutorService.findById(0).get());
		noticia.setFechaPublicacion(LocalDate.now());
		noticia.setImagen("prueba.jpg");
		noticia.setName("AdaByron");
		noticia.setTexto("It is a long established fact that a reader will be distracted by the readable content of a page when looking");

		this.noticiaService.save(noticia);
		noticias = this.noticiaService.findAll();
		
		assertThat(noticias.size()).isEqualTo(found +1);
	
	}
	
	@Test
	public void shouldNotInsertNoticia() {
		Noticia noticia = new Noticia();
		noticia.setAutor(tutorService.findById(0).get());
		noticia.setFechaPublicacion(LocalDate.now());
		noticia.setImagen("");
		noticia.setName("AdaByron");
		noticia.setTexto("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");

		Assertions.assertThrows(ConstraintViolationException.class, ()->{
			this.noticiaService.save(noticia);
		});
		
	}
	
	@Test
	public void shouldFindNoticia() {
		Noticia noticia = this.noticiaService.findById(0).get();
		String name = noticia.getName();
		
		assertThat(noticia.getName()).isEqualTo(name);
	}
	
	@Test
	public void shouldNotFindNoticia() {
		Assertions.assertThrows(Exception.class, ()->{
			Noticia noticia = this.noticiaService.findById(99).get();
		});
		
	}
	
	
	@Test
	public void shouldFindAllNoticia() {
		Collection<Noticia> noticias = this.noticiaService.findAll();
		assertThat( noticias.size()).isEqualTo(12);
	}
	
	@Test
	public void shouldUpdateNoticia() {
		Noticia noticia = this.noticiaService.findById(0).get();
		String antiguoNombre = noticia.getName();
		String nuevoNombre = noticia.getName()+"colgado";
		
		noticia.setName(nuevoNombre);
		this.noticiaService.save(noticia);
		
		noticia = this.noticiaService.findById(0).get();
		assertThat(noticia.getName()).isEqualTo(nuevoNombre);
		assertNotEquals(antiguoNombre, noticia.getName(), "Este nombre no coincide con el nombre actual ");
		
	}
	
	@Test
	public void shouldNotUpdateNoticia() {
		Noticia noticia = this.noticiaService.findById(0).get();
		Set<Tutor> autores = new HashSet<Tutor>();
		noticia.setAutores(autores);
		
			this.noticiaService.save(noticia);
			assertThat(noticia.getAutores().size()).isEqualTo(0);
		
			
	}
	
	@Test
	public void shouldDeleteNoticia() {
		Collection<Noticia> noticias = this.noticiaService.findAll();
		Integer numNoticiasOld = noticias.size();
		noticiaService.delete(noticiaService.findById(1).get());
		
		Integer numNoticiasNew = this.noticiaService.findAll().size();
		
		assertThat(numNoticiasNew).isEqualTo(this.noticiaService.findAll().size());
		assertNotEquals(numNoticiasOld, numNoticiasNew, "El numero de noticias no coincide");
	}
	@Test
	public void shouldFindNoticiaByTutorInitial() {
		Collection<Noticia> noticias = this.noticiaService.findNoticiasByTutor(0);
		assertThat( noticias.size()).isEqualTo(4);
	}

}
