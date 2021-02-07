package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArticuloServiceTests {
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	TutorService tutorService;
	
	@Test
	public void shouldFindAll() {
		assertThat(articuloService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindArticuloById() {
		Articulo articulo= this.articuloService.findById(0).get();
		assertThat(articulo.getId()).isEqualTo(0);
		assertThat(articulo.getFechaPublicacion()).isEqualTo("23 de Julio de 2020");
		assertThat(articulo.getImagen()).isEqualTo("resources/images/articulos/2020122317810299000000.jpg");
		assertThat(articulo.getName()).isEqualTo("Articulo sobre DBGames");
		assertThat(articulo.getTexto()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt"
				+ " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea"
				+ " commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
				+ " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
		Set<Tutor> st = new HashSet<Tutor>();
		st.add(this.tutorService.findById(0).get());
		st.add(this.tutorService.findById(1).get());
		st.add(this.tutorService.findById(2).get());
		st.add(this.tutorService.findById(3).get());
		assertThat(articulo.getAutores()).isEqualTo(st);
	}
	
	@Test
	public void shouldInsertArticulo() {
		Collection<Articulo> articulos = this.articuloService.findAll();
		int found = articulos.size();
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(this.tutorService.findById(0).get());
		autores.add(this.tutorService.findById(1).get());
		
		Articulo articulo = new Articulo();
		articulo.setAutores(autores);
		articulo.setFechaPublicacion(LocalDate.now());
		articulo.setImagen("prueba.jpg");
		articulo.setName("NEw Lorem");
		articulo.setTexto("Lorem not ipsum");

		this.articuloService.save(articulo);
		articulos = this.articuloService.findAll();
		
		assertThat(articulos.size()).isEqualTo(found +1);
	}
	
	@Test
	public void shouldNotInsertArticulo() {
		Set<Tutor> autores = new HashSet<Tutor>();
		autores.add(this.tutorService.findById(0).get());
		autores.add(this.tutorService.findById(1).get());
		
		Articulo articulo = new Articulo();
		articulo.setAutores(autores);
		articulo.setFechaPublicacion(LocalDate.now());
		articulo.setImagen("prueba.jpg");
		articulo.setName("NEw Lorem");

		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			this.articuloService.save(articulo);
		});
	}
	
	@Test
	public void shouldUpdateArticulo() {
		Articulo articulo = this.articuloService.findById(0).get();
		String antiguoNombre = articulo.getName();
		String nuevoNombre = "new "+ articulo.getName();
		
		articulo.setName(nuevoNombre);
		this.articuloService.save(articulo);
		
		articulo = this.articuloService.findById(0).get();
		assertThat(articulo.getName()).isEqualTo(nuevoNombre);
		assertNotEquals(antiguoNombre, articulo.getName(), "Este nombre es diferente al nombre actual ");
		
	}
	
	@Test
	public void shouldDeleteArticulo() {
		Collection<Articulo> articulos = this.articuloService.findAll();
		Integer numArticulosOld = articulos.size();
		articuloService.delete(articuloService.findById(1).get());
		
		Integer numArticulosNew = this.articuloService.findAll().size();
		
		assertThat(numArticulosNew).isEqualTo(this.articuloService.findAll().size());
		assertNotEquals(numArticulosOld, numArticulosNew, "El numero de articulos no coincide");
	}
	
	@Test
	public void shouldfindArticulosByTutor() {
		Collection<Articulo> ca = this.articuloService.findArticulosByTutor(0);
		Set<Articulo> st = new HashSet<>();
		for(Articulo a : this.articuloService.findAll()) {
			if(a.getAutores().contains(this.tutorService.findById(0).get())) {
				st.add(a);
			}
		}
		assertThat(ca.stream().collect(Collectors.toSet())).isEqualTo(st);
	}
	
	@Test
	public void shouldfindArticulosByTutorPage() {
		Integer ArticulosDeTutorPage = articuloService.findArticulosByTutorPage(0,PageRequest.of(0, 1, Sort.by("fecha_publicacion").descending())).getSize();
		assertThat(ArticulosDeTutorPage).isEqualTo(1);
	}

}
