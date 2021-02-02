package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import javax.swing.text.View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.PublicacionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=PublicacionController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class PublicacionControllerTests {
	
	private static final int TEST_PUBLICACION_ID = 0;
	private static final int TEST_ALUMNO_ID = 0;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ProblemaService problemaService;

	@MockBean
	private PublicacionService publicacionService;
	@MockBean
	private AlumnoService alumnoService;
	
	private Publicacion publicacion;
	
	private Alumno alumno;
	
	@BeforeEach
	void setup() {
		alumno = new Alumno();
		alumno.setId(TEST_ALUMNO_ID);
		
		publicacion = new Publicacion();
		publicacion.setId(TEST_PUBLICACION_ID);
		publicacion.setAlumno(alumno);
		publicacion.setFecha(LocalDateTime.of(2021, 2, 1, 16, 39));
		publicacion.setTexto("Nueva publicación test");
	}
	
	@WithMockUser(value = "spring",authorities={"tutor"})
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/foro")
						.with(csrf())
						.param("texto", "Bedilia es la mejor profe"))
			.andExpect(status().isOk());
	}
	
}
