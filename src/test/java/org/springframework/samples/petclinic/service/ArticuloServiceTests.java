package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.This;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArticuloServiceTests {
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	TutorService tutorService;
	
	@Test
	public void shouldInsertArticulo() {
		Collection<Articulo> articulos = this.articuloService.findAll();
		int found = articulos.size();
		
		Articulo articulo = new Articulo();
		articulo.setAutor(tutorService.findById(0).get());
		articulo.setFechaPublicacion(LocalDate.now());
		articulo.setImagen("prueba.jpg");
		articulo.setName("NEw Lorem");
		articulo.setTexto("Lorem not ipsum");

		this.articuloService.save(articulo);
		articulos = this.articuloService.findAll();
		
		assertThat(articulos.size()).isEqualTo(found +1);
	
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

}
