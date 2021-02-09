package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Auth;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuthServiceTests {

	@Autowired
	AuthService authService;

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	TutorService tutorService;

	@Autowired
	CreadorService creadorService;

	@Autowired
	AdministradorService administradorService;

	Auth auth;

	@Test
	public void shouldSaveAuth() {
		Integer size = authService.authCount();
		Auth at = new Auth();
		at.setAlumno(alumnoService.findById(0).get());
		at.setAuthority("alumno");
		authService.saveAuth(at);
		assertThat(authService.authCount()).isEqualTo(size + 1);
	}
	
	@Test
	public void shoulSaveAuthAdministrador() {
		authService.saveAuthoritiesAdministrador("administrador1@us.es","administrador");
		assertThat(authService.findById(authService.authCount()-1).get().getAuthority()).isEqualTo("administrador");
	}

	@Test
	public void shoulSaveAuthAlumno() {
		authService.saveAuthoritiesAlumno("rarmon@alum.us.es","alumno");
		assertThat(authService.findById(authService.authCount()).get().getAuthority()).isEqualTo("alumno");
	}

	@Test
	public void shoulSaveAuthCreador() {
		authService.saveAuthoritiesCreador("davbrican@us.es","creador");
		assertThat(authService.findById(authService.authCount()+1).get().getAuthority()).isEqualTo("creador");
	}
	
	@Test
	public void shoulSaveAuthTutor() {
		authService.saveAuthoritiesTutor("alebarled@alum.us.es","tutor");
		assertThat(authService.findById(authService.authCount()+3).get().getAuthority()).isEqualTo("tutor");
	}

}
