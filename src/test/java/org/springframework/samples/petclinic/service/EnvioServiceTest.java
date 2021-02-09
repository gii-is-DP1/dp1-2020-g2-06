package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.samples.petclinic.model.Alumno;
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
		assertThat(envioService.findAll().size()).isEqualTo(20);
	}
	

	@Test
	public void shouldFindById() {
		Envio e = envioService.findById(0).get();
		assertThat(e.getIdJudge()).isEqualTo(0);
		assertThat(e.getFecha().toString()).isEqualTo("2020-08-21T11:13:13.274");
		assertThat(e.getCodigoPath()).isEqualTo("codes/202119235052330851200.java");
		assertThat(e.getResolucion()).isEqualTo("AC");
		assertThat(e.getAlumno().getId()).isEqualTo(1);
		assertThat(e.getProblema().getId()).isEqualTo(1);
		assertThat(e.getSeason().getId()).isEqualTo(1);
		assertThat(e.getSeasonYear()).isEqualTo(2020);
	}
	
	@Test
	public void shouldNotFindById() {
		Optional <Envio> e = envioService.findById(envioService.findAll().size());
		assertThat(e.isPresent()).isEqualTo(false);
	}
	
	@Test
	public void shouldFindAllByAlumno() {
		assertThat(envioService.findAllByAlumno(0).size()).isEqualTo(7);
	}
	
	@Test
	public void shouldNotFindAllByAlumno() {
		int envios = envioService.findAllByAlumno(envioService.findAll().size()).size();
		assertThat(envios).isEqualTo(0);
	}
	
	
	
	@Test
	public void shouldFindAllByAlumnoPage() {
		Pageable pageableA = PageRequest.of(0, 5, Sort.by("id").descending());
		int envios = envioService.findAllByAlumnoPage(pageableA, 0).getSize();
		assertThat(envios).isEqualTo(5);
	}
	
	@Test
	public void shouldFindAllByProblema() {
		assertThat(envioService.findAllByProblema(0).size()).isEqualTo(5);
	}
	
	
	@Test
	public void shouldFindAllByProblemaPage() {
		Pageable pageableA = PageRequest.of(0, 5, Sort.by("id").descending());
		int envios = envioService.findAllByProblemaPage(pageableA, 0).getSize();
		assertThat(envios).isEqualTo(5);
	}
	
	@Test
	public void shouldFindResolucionProblema() {
		Map<String, Long> mapa = new HashMap<>();
		mapa.put("AC",4L);
		mapa.put("TLE", 1L);
		assertThat(envioService.resolucionProblema(0)).isEqualTo(mapa);
	}
	
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
	
	
	@Test
	public void shouldFindAllByAlumnoAc() {
		int size = envioService.findAllByAlumnoAc(0).size();
		assertThat(size).isEqualTo(2);
	}
	
	
	@Test
	public void shouldFindAllByAlumnoWa() {
		int size = envioService.findAllByAlumnoWa(0).size();
		assertThat(size).isEqualTo(1);
	}
	
	@Test
	public void shouldFindAlumnosAC() {
		int n = envioService.alumnosAC(0);
		assertThat(n).isEqualTo(2);
	}
	
	

}
