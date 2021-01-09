package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.Competicion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CompeticionServiceTests {

	@Autowired
	CompeticionService competicionService;
	
	@Test
	void shouldUpdateCompeticion() {
		Competicion competicion = this.competicionService.findById(0).get();
		LocalDate fechaInicioAntigua = competicion.getFecha_inicio();
		LocalDate fechaInicioNueva = LocalDate.now();
		 
		competicion.setFecha_inicio(fechaInicioNueva);
		this.competicionService.save(competicion);
		assertThat(competicion.getFecha_inicio()).isEqualTo(fechaInicioNueva);
		assertNotEquals(fechaInicioAntigua, competicion.getFecha_inicio(), "Esta fecha no coincide con la fecha actual de la competicion");
	}
	
	@Test
	public void shouldInsertCompeticion() {
		Collection<Competicion> competiciones = this.competicionService.findAll();
		int found = competiciones.size();
		
		Competicion competicion = new Competicion();

		
		competicion.setDescripcion("Competicion test");
		competicion.setFecha_inicio(LocalDate.of(2021, 01, 15));
		competicion.setFecha_fin(LocalDate.of(2021, 02, 15));
		competicion.setHora_inicio(LocalTime.of(12, 0));
		competicion.setHora_fin(LocalTime.of(12, 0));
		competicion.setImagen("resources/images/problemas/20201223171314927000000.jpg");
		competicion.setNombre("Testing Compe");
		
		
		
		this.competicionService.save(competicion);
		competiciones = this.competicionService.findAll();
		
		assertThat(competiciones.size()).isEqualTo(found +1);
	}
}
