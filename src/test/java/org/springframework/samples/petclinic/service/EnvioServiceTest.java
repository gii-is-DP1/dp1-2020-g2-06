package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EnvioServiceTest {
	
	@Autowired
	EnvioService envioService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	ProblemaService problemaService;
	
	@Test
	public void shouldFindAll() {
		assertThat(envioService.findAll().size()).isEqualTo(11);
	}
	
	@Test
	public void shouldFindById() {
		Envio e = envioService.findById(0).get();
		assertThat(e.getIdJudge()).isEqualTo(0);
		assertThat(e.getFecha().toString()).isEqualTo("2020-08-21T11:13:13.274");
		assertThat(e.getCodigoPath()).isEqualTo("codes/prueba.java");
		assertThat(e.getResolucion()).isEqualTo("AC");
		assertThat(e.getAlumno().getId()).isEqualTo(0);
		assertThat(e.getProblema().getId()).isEqualTo(1);
		assertThat(e.getSeason().getId()).isEqualTo(0);
		assertThat(e.getSeasonYear()).isEqualTo(2020);
	}
	
	@Test
	public void shouldFindAllByAlumno() {
		assertThat(envioService.findAllByAlumno(0).size()).isEqualTo(8);
	}
	
	@Test
	public void shouldFindResolucionProblema() {
		Map<String, Long> mapa = new HashMap<>();
		mapa.put("AC",3L);
		mapa.put("TLE", 1L);
		assertThat(envioService.resolucionProblema(0)).isEqualTo(mapa);
	}
	
	//INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (10,10,'2019-05-22T11:13:13.274','codes/prueba.java','AC',1,3,1,2019);
	@Test
	public void shouldSave() {
		Envio e = new Envio();
		Temporada t = new Temporada();
		int size = envioService.findAll().size();
		
		t.setId(0);
		t.setNombre("PRIMAVERA");
	
		e.setIdJudge(10);
		e.setFecha(LocalDateTime.now());
		e.setCodigoPath("codes/prueba.java");
		e.setResolucion("CE");
		e.setAlumno(alumnoService.findById(0).get());
		e.setProblema(problemaService.findById(0).get());
		e.setSeason(t);
		e.setSeasonYear(2020);
		envioService.save(e);
		
		int size2 = envioService.findAll().size();
		assertThat(size2).isEqualTo(size+1);
	}

}
