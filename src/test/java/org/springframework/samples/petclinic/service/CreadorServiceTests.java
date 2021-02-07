package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CreadorServiceTests {
	
	@Autowired
	CreadorService creadorService;

	@Autowired
	ProblemaService problemaService;

	
	
	@Test
	public void shouldFindAll() {
		assertThat(creadorService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindCreadorById() {
		Creador creador= this.creadorService.findById(0).get();
		assertThat(creador.getId()).isEqualTo(0);
		assertThat(creador.getNombre()).isEqualTo("David");
		assertThat(creador.getApellidos()).isEqualTo("Brincau Cano");
		assertThat(creador.getEmail()).isEqualTo("davbrican@us.es");
		
		assertThat(creador.getImagen()).isEqualTo("resources/images/creadores/2020122317244979000000.jpg");
		assertThat(creador.getPass()).isEqualTo("$2a$10$p25p6pDKzt4O/ez2VhFWFuu6P7e9HDNYxFIURR/r0ZoTi9NnKUJgO");
	}
	
	@Test
	public void shouldInsertCreador() {
		Collection<Creador> creadores = this.creadorService.findAll();
		int found = creadores.size();

		Creador creador = new Creador();
		creador.setEmail("vicgragil@us.es");
		creador.setNombre("Victor");
		creador.setApellidos("Granero Gil");
		creador.setPass("dbgamesnosirvWe2$");
                
		this.creadorService.save(creador);
		creadores = this.creadorService.findAll();
		
		assertThat(creadores.size()).isEqualTo(found + 1);
	}
	
	
	
	@Test
	void shouldUpdateOwner() {
		Creador creador = this.creadorService.findById(0).get();
		String antiguoNombre = creador.getNombre();
		String nuevoNombre = creador.getNombre()+"x";
		
		creador.setNombre(nuevoNombre);
		this.creadorService.save(creador);
		
		creador = this.creadorService.findById(0).get();
		assertThat(creador.getNombre()).isEqualTo(nuevoNombre);
		assertNotEquals(antiguoNombre, creador.getNombre(), "Este nombre no coincide con el nombre actual del creador");
	}
	
	@Test
	void shouldFindById(){
		assertThat(creadorService.findById(0));
	}
	
	
	@Test
	void shoulInsertProblema() {
		Creador creador = this.creadorService.findById(0).get();
		Integer numProblemasAntiguos = problemaService.findAllByCreador(creador.getId()).size();
		
		Problema problemaNuevo = new Problema();
		Temporada t = new Temporada();
		t.setId(0);
		t.setNombre("PRIMAVERA");
		problemaNuevo.setName("Test problem");
		problemaNuevo.setCreador(creador);
		problemaNuevo.setPuntuacion(2);
		problemaNuevo.setSeason(t);
		problemaNuevo.setSalida_esperada("0 2 1 12 12");
		problemaNuevo.setCasos_prueba("2 3 34 23 2 23");
		problemaNuevo.setDescripcion("Problema de prueba.Pruébame :P");
		problemaNuevo.setSeasonYear(2021);
		problemaNuevo.setDificultad("MEDIA");
		problemaService.saveProblema(problemaNuevo);
		
		creador = this.creadorService.findById(0).get();
		Integer numProblemasNuevo = problemaService.findAllByCreador(creador.getId()).size();
		assertThat(numProblemasNuevo).isEqualTo(numProblemasAntiguos+1);
		assertNotEquals(numProblemasAntiguos, numProblemasNuevo, "El número de problemas asociado a este creador no es correcto");
	}
}
