package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Competicion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CompeticionServiceTests {

	@Autowired
	CompeticionService competicionService;
	
	@Test
	void shouldUpdateCompeticion() {
		Competicion competicion = this.competicionService.findById(0).get();
		LocalDateTime fechaInicioAntigua = competicion.getFecha_inicio();
		LocalDateTime fechaInicioNueva = LocalDateTime.now();
		 
		competicion.setFecha_inicio(fechaInicioNueva);
		this.competicionService.save(competicion);
		assertThat(competicion.getFecha_inicio()).isEqualTo(fechaInicioNueva);
		assertNotEquals(fechaInicioAntigua, competicion.getFecha_inicio(), "Esta fecha no coincide con la fecha actual de la competicion");
	}
	
}
