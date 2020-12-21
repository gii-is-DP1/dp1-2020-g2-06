package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TutorServiceTests {
	
	@Autowired
	TutorService tutorService;
	
	@Autowired
	NoticiaService noticiaService;
	
	@Autowired
	ArticuloService articuloService;
	
	@Test
	public void shouldFindAll() {
		assertThat(tutorService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindTutorById() {
		Tutor tutor= this.tutorService.findById(0).get();
		assertThat(tutor.getId()).isEqualTo(0);
		assertThat(tutor.getNombre()).isEqualTo("Alejandro");
		assertThat(tutor.getApellidos()).isEqualTo("Barranco Ledesma");
		assertThat(tutor.getEmail()).isEqualTo("alebarled@alum.us.es");
		assertThat(tutor.getFoto()).isEqualTo("https://estaticos.elperiodico.com/resources/jpg/6/4/img-8878-1585501756946.jpg");
		assertThat(tutor.getPass()).isEqualTo("r4rm0n");
	}
	
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
	void shouldUpdateTutor() {
		Tutor tutor = this.tutorService.findById(1).get();
		String antiguoNombre = tutor.getNombre();
		String nuevoNombre = tutor.getNombre()+"x";
		
		tutor.setNombre(nuevoNombre);
		this.tutorService.save(tutor);
		
		tutor = this.tutorService.findById(1).get();
		assertThat(tutor.getNombre()).isEqualTo(nuevoNombre);
		assertNotEquals(antiguoNombre, tutor.getNombre(), "Este nombre no coincide con el nombre actual del tutor");
	}
	
	
	@Test
	void shoulInsertNoticia() {
		Tutor tutor = this.tutorService.findById(0).get();
		Integer numNoticiasAntiguos = noticiaService.findTutorNoticias(tutor.getId()).size();
		
		Noticia noticiaNueva = new Noticia();
		noticiaNueva.setAutor(tutor);
		noticiaNueva.setFechaPublicacion(LocalDate.now());
		noticiaNueva.setImagen("https://eina.unizar.es/sites/eina.unizar.es/files/concurso_adabyron.jpg");
		noticiaNueva.setName("Concurso de AdaByron");
		noticiaNueva.setTexto("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");
		noticiaService.save(noticiaNueva);
		
		tutor = this.tutorService.findById(0).get();
		Integer numNoticiasNuevo = noticiaService.findTutorNoticias(tutor.getId()).size();
		assertThat(numNoticiasNuevo).isEqualTo(numNoticiasAntiguos+1);
		assertNotEquals(numNoticiasAntiguos, numNoticiasNuevo, "El número de noticias asociado a este tutor no es correcto");
	}
	
	@Test
	void shoulInsertArticulo() {
		Tutor tutor = this.tutorService.findById(0).get();
		Integer numArticulosAntiguos = articuloService.findArticulosByTutor(tutor.getId()).size();
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(tutor);
		Articulo articuloNuevo = new Articulo();
		articuloNuevo.setAutores(autores);
		articuloNuevo.setFechaPublicacion(LocalDate.now());
		articuloNuevo.setName("Concurso de AdaByron");
		articuloNuevo.setImagen("foto.jpg");
		articuloNuevo.setTexto("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");
		articuloService.save(articuloNuevo);
		
		tutor = this.tutorService.findById(0).get();
		Integer numArticulosNuevo = articuloService.findArticulosByTutor(tutor.getId()).size();
		assertThat(numArticulosNuevo).isEqualTo(numArticulosAntiguos+1);
		assertNotEquals(numArticulosAntiguos, numArticulosNuevo, "El número de noticias asociado a este tutor no es correcto");
	}
}
