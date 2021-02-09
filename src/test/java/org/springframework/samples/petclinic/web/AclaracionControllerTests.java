package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class AclaracionControllerTests {
	
	private static final int TEST_TUTOR_ID = 0;
	private static final int TEST_PROBLEMA_ID = 0;
	
	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	ProblemaService problemaService;
	
	@MockBean
	TutorService tutorService;
	
	@MockBean
	private AuthService authService;
	
	private Tutor tutor;
	
	private Problema problema;

	
	@BeforeEach
		void setup() {
		
			mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
			
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
	
	//Caso positivo de HU-16+E1 Aclaraciones en Problemas
	@WithMockUser(username="jesus@us.es",authorities="tutor")
    @Test
    void testProcessCreationFormSuccessAsTutor() throws Exception {
		mockMvc.perform(post("/aclaraciones/new")
						.with(csrf())
						.param("texto", "El problema se puede resolver en 2 líneas")
						.param("idProblema", "0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/problemas/0"));
	}
	
	//Caso negativo de HU-16-E1 Aclaraciones en Problemas
	@WithMockUser(username = "jesus@us.es",authorities="tutor")
    @Test
    void testProcessCreationFormFailWithTextAttributeEmptyAsTutor() throws Exception {
		mockMvc.perform(post("/aclaraciones/new")
						.with(csrf())
						.param("texto", "")
						.param("idProblema", "0"))
			.andExpect(model().hasErrors());
	}
	
	//Caso negativo de HU-16-E2 Aclaraciones en Problemas
	@WithMockUser(value = "spring",authorities="creador")
    @Test
    void testProcessCreationFormClientErrorAsCreador() throws Exception {
		mockMvc.perform(get("/aclaraciones/new"))
						.andExpect(status().is4xxClientError());
	}
	
}
