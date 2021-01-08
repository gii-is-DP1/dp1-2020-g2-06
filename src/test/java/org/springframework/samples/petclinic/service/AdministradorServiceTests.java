package org.springframework.samples.petclinic.service;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.service.AdministradorService;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdministradorServiceTests {

	@Autowired
	AdministradorService administradorService;
	
	@Test
	public void shouldFindAll() {
		assertThat(administradorService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindAdministradorById() {
		Administrador administrador = this.administradorService.findById(0).get();
		assertThat(administrador.getId()).isEqualTo(0);
		assertThat(administrador.getEmail()).isEqualTo("administrador@us.es");
		assertThat(administrador.getPass()).isEqualTo("adm1asdfWW%n1234");
	}
	
	@Test
	public void shouldUpdateAdminitrador() {
		Administrador administrador = this.administradorService.findById(0).get();
		String antiguoEmail = administrador.getEmail();
		String nuevoEmail = "administrador2@us.es";
		
		administrador.setEmail(nuevoEmail);
		this.administradorService.save(administrador);
		
		administrador = this.administradorService.findById(0).get();
		assertThat(administrador.getEmail()).isEqualTo(nuevoEmail);
		assertNotEquals(antiguoEmail, administrador.getEmail(), "Este email no coincide con el email actual del administrador");
		
	}
	
	@Test
	public void shouldInsertAdministrador() {
		Collection<Administrador> administradores = administradorService.findAll();
		Integer aOld = administradores.size();
		Administrador administrador = new Administrador();
		administrador.setEmail("pacooooo@us.es");
		administrador.setPass("HolasoyPepe44#@");
		administradorService.save(administrador);
		Integer aNew = administradorService.findAll().size();
		assertNotEquals(aOld, aNew);
		assertThat(aOld+1).isEqualTo(aNew);
	}
	
}
