package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AlumnoServiceTests {
				
		@Autowired
		private AlumnoService alumnoService;
		
		@Test
		public void shouldFindAll() {
			assertThat(alumnoService.findAll().size()).isGreaterThan(0);
		}
		
		@Test
		public void shouldFindAlumnoById() {
			Alumno alumno= this.alumnoService.findById(0).get();
			assertThat(alumno.getId()).isEqualTo(0);
			assertThat(alumno.getNombre()).isEqualTo("Daniel");
			assertThat(alumno.getApellidos()).isEqualTo("Montes");
			assertThat(alumno.getEmail()).isEqualTo("rarmon@alum.us.es");
			assertThat(alumno.getImagen()).isEqualTo("resources/images/alumnos/20201223154714879157200.jpg");
			assertThat(alumno.getPass()).isEqualTo("octave¬11El0ver");
			assertThat(alumno.getCompartir()).isEqualTo(true);
		}
		
		@Test
		public void shouldNotFindAlumnoById() {
			Optional<Alumno> alumno= this.alumnoService.findById(2555);
			assertThat(alumno.isPresent()).isEqualTo(false);
		}
		
		@Test
		public void shouldSaveAlumno() {
			Alumno alumno = new Alumno();
			alumno.setNombre("Carmen");
			alumno.setApellidos("Barra");
			alumno.setEmail("carbarmen@alum.us.es");
			alumno.setImagen("resources/images/alumnos/20201223154714879157200.jpg");
			alumno.setPass("pass1DD234%");
			alumnoService.save(alumno);
			String email = alumnoService.findById(3).get().getEmail();
			
			assertThat(alumno.getEmail()).isEqualTo(email);
		}
		
		@Test
		public void shouldNotSaveAlumno() {
			Alumno alumno = new Alumno();
			try {
			alumno.setNombre("Carmen");
			alumno.setApellidos("Barra");
			alumno.setEmail("carbarmen@hotmail.es");
			alumno.setImagen("resources/images/alumnos/20201223154714879157200.jpg");
			alumno.setPass("pASS44@@");
			alumnoService.save(alumno);
			}
			catch(Exception e) {
				
			}
			Optional<Alumno> opt = alumnoService.findById(7);
			
			assertThat(opt.isPresent()).isEqualTo(false);
		}
		
		@Test
		public void shouldReturnProblemasResueltos() {
			Collection<Problema> problemas = this.alumnoService.problemasResueltos(0);
			
			assertThat(problemas.size()).isEqualTo(1);
		}
		
		@Test
		public void shouldReturnProblemasResueltosThisYear() {
			Collection<Problema> problemas = this.alumnoService.problemasResueltos(0);
				
			assertThat(problemas.size()).isEqualTo(1);
		}
		
		@Test
		public void problemasResueltosInASeason(){
			Collection<Problema> problemas = this.alumnoService.problemasResueltos(0);
			Optional<Alumno> alumno = this.alumnoService.findById(0);
			Set<Problema> st = new HashSet<Problema> ();
			for(Envio a : alumno.get().getEnvios()) {
				if(a.getResolucion().equals("AC") && a.getSeason().equals(Utils.getActualSeason()) && a.getSeasonYear().equals(Utils.getActualYearofSeason())) {
					st.add(a.getProblema());
				}
			}
			assertThat(problemas.stream().collect(Collectors.toSet())).isEqualTo(st);
		}
		
}
