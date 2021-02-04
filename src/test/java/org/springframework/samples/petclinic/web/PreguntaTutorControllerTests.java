package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.PreguntaTutorService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
public class PreguntaTutorControllerTests {

	private static final int TEST_PREGUNTATUTOR_ID = 0;
	
	private MockMvc mockMvc;
	
	@MockBean
	AlumnoService alumnoService;
	
	@MockBean
	ProblemaService problemaService;
	
	@MockBean
	TutorService tutorService;
	
	@MockBean
	ProblemaController problemaController;
	
	@MockBean
	TutorController tutorController;
	
	@MockBean
	PreguntaTutorService preguntaTutorService;
	
	
	 @Autowired
	 private WebApplicationContext context;
	
	@BeforeEach
	void setup() throws IOException {
	mockMvc = MockMvcBuilders
	          .webAppContextSetup(context)
	          .apply(SecurityMockMvcConfigurers.springSecurity())
	          .build();
	given(problemaService.findById(Mockito.anyInt())).willReturn(Optional.of(new Problema()));
	given(alumnoService.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Alumno()));
	given(preguntaTutorService.findById(Mockito.anyInt())).willReturn(Optional.of(new PreguntaTutor()));
	given(tutorService.findById(Mockito.anyInt())).willReturn(Optional.of(new Tutor()));
	given(problemaController.problemaDetails(Mockito.anyInt(), Mockito.any(ModelMap.class))).willReturn("/problemas/problemaDetails");
	given(tutorController.tutorDetails(Mockito.anyInt(), Mockito.any(ModelMap.class))).willReturn("/tutores/tutorDetails");
	}
	
	@WithMockUser(value="spring", authorities = "alumno")
	@Test
	void testProcessCreationFormSuccessAsAlumno() throws Exception{
		mockMvc.perform(post("/preguntatutor/new")
				.with(csrf())
				.param("pregunta", "Hola buenas tardes")
				.param("idProblema", "0")
				)
		.andExpect(status().isOk())
		.andExpect(view().name("/problemas/problemaDetails"));
	}
	
	@WithMockUser(value="spring", authorities = "tutor")
	@Test
	void testProcessCreationFormFailureAsAlumno() throws Exception{
		mockMvc.perform(post("/preguntatutor/new")
				.with(csrf())
				.param("pregunta", "Hola buenas tardes")
				.param("idProblema", "0")
				)
		.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value="spring", authorities = "tutor")
	@Test
	void testProcessCreationFormSuccessAsTutor() throws Exception{
		mockMvc.perform(post("/preguntatutor/answer")
				.with(csrf())
				.param("respuesta", "Hola buenas tardes")
				.param("idTutor", "0")
				.param("preguntaTutor", "0")
				)
		.andExpect(status().isOk())
		.andExpect(view().name("/tutores/tutorDetails"));
	}
	
	@WithMockUser(value="spring", authorities = "alumno")
	@Test
	void testProcessCreationFormFailureAsTutor() throws Exception{
		mockMvc.perform(post("/preguntatutor/answer")
				.with(csrf())
				.param("respuesta", "Hola buenas tardes")
				.param("idTutor", "0")
				.param("preguntaTutor", "0")
				)
		.andExpect(status().is4xxClientError());
	}
}
