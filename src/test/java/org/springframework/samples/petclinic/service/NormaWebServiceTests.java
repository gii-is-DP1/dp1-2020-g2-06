package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class NormaWebServiceTests {
	@Autowired
	private NormaWebService normaWebService;
	
	@Test
	public void testCountWithInitialData() {
		assertThat(normaWebService.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldInsertNormaWeb() {
		Collection<NormaWeb> normasWeb = this.normaWebService.findAll();
		int found = normasWeb.size();

		NormaWeb normaWeb = new NormaWeb();
		normaWeb.setName("ComNormaWebiciones");
		normaWeb.setDescripcion("Solo se puede participar en una comNormaWebicion a la vez");
                
		this.normaWebService.saveNormaWeb(normaWeb);	
		
		Collection<NormaWeb> normasWeb2 = this.normaWebService.findAll();
		
		assertThat(normasWeb2.size()).isEqualTo(found + 1);
	}
	
	@Test
	public void shouldThrowExceptionInsertingNormaWebWithoutDescripcion() {
		NormaWeb normaWeb = new NormaWeb();
		normaWeb.setName("Lorem");
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			this.normaWebService.saveNormaWeb(normaWeb);
		});	
	}
	
	@Test
	public void shouldFindNormaWeb() {
		NormaWeb normaWeb = this.normaWebService.findById(0).get();
		String name = "Respeta";
		
		assertThat(normaWeb.getName()).isEqualTo(name);
	}
	
	@Test
	public void shouldNotFindNormaWeb() {
		Assertions.assertThrows(NoSuchElementException.class, () ->{
			this.normaWebService.findById(50).get();
		});	
	}
	
	@Test
	public void shouldFindAllNormaWebInitial() {
		Collection<NormaWeb> normasWeb = this.normaWebService.findAll();

		assertThat( normasWeb.size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	void shouldUpdateNormaWeb() {
		NormaWeb normaWeb = this.normaWebService.findById(0).get();
		String oldDescripcion = normaWeb.getDescripcion();
		String newDescripcion = oldDescripcion + "X";

		normaWeb.setDescripcion(newDescripcion);
		this.normaWebService.saveNormaWeb(normaWeb);

		// retrieving new name from database
		normaWeb = this.normaWebService.findById(0).get();
		assertThat(normaWeb.getDescripcion()).isEqualTo(newDescripcion);
	}
	
	@Test
	public void shouldDeleteNormaWeb() {
		Collection<NormaWeb> normasWeb = this.normaWebService.findAll();
		int found = normasWeb.size();
                
		NormaWeb normaweb = normaWebService.findById(0).get();
		this.normaWebService.delete(normaweb);
		
		Collection<NormaWeb> normasWeb2 = this.normaWebService.findAll();
		
		assertThat(normasWeb2.size()).isEqualTo(found - 1);
	}
}
