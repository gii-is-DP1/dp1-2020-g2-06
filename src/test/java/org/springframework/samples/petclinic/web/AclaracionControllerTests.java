package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=AclaracionController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class AclaracionControllerTests {
	
	private static final int TEST_TUTOR_ID = 0;
	private static final int TEST_PROBLEMA_ID = 0;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ProblemaService problemaService;
	
	@MockBean
	TutorService tutorService;
	
	@MockBean
	AclaracionService aclaracionService;
	
	@MockBean
	private AuthService authService;
	
	private Tutor tutor;
	
	private Problema problema;

	
	
	@BeforeEach
		void setup() {
			tutor = new Tutor();
			tutor.setId(TEST_TUTOR_ID);
			
			problema = new Problema();
			problema.setId(TEST_PROBLEMA_ID);
			problema.setDescripcion("hola");
			problema.setCasos_prueba("0 0 0");
			problema.setSalida_esperada("1 1 1");
			problema.setSeasonYear(1);
			Temporada aux = new Temporada();
			aux.setNombre("Invierno");
			problema.setSeason(aux);
			
			given(problemaService.findById(any(Integer.class))).willReturn(Optional.of(problema));
			given(tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(tutor));
			
	
	}
	
	@WithMockUser(value = "spring",authorities="tutor")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/aclaraciones/new")
						.with(csrf())
						.param("texto", "El tipo a usar es long")
						.param("idProblema", "0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/problemas/0"));
	}
	
	@WithMockUser(value = "spring",authorities="creador")
    @Test
    void testProcessCreationFormAsCreador() throws Exception {
		mockMvc.perform(post("/aclaraciones/new")
						.with(csrf())
						.param("texto", "El tipo a usar es long")
						.param("idProblema", "0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/problemas/0"));
	}
	
}
