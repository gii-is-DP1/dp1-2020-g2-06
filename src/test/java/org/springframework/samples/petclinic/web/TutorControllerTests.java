package org.springframework.samples.petclinic.web;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=TutorController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class TutorControllerTests {

	private static final int TEST_TUTOR_ID = 0;
	
	@Autowired
	private TutorController tutorController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private ArticuloService articuloService;
	
	@MockBean
	private NoticiaService noticiaService;
	
	@MockBean
	private AuthService authService;
	
	private Tutor tutor;
	
	
	@BeforeEach
		void setup() {
			Optional<Tutor> t = Optional.empty();
//			Optional<Articulo> a = Optional.empty();
//			Optional<Noticia> n = Optional.empty();
			tutor = new Tutor();
			tutor.setId(TEST_TUTOR_ID);
			tutor.setNombre("Pepe");
			tutor.setApellidos("Alvarez Toledo");
			tutor.setEmail("codetutor@us.es");
			tutor.setImagen("/resources/images/pets.png");
			tutor.setPass("Codeus@49lsañkfjnsafsa");
			Optional.of(tutor);
			given(this.tutorService.findById(TEST_TUTOR_ID)).willReturn(t);
			
			
	
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/tutores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("tutor"))
		.andExpect(view().name("tutores/createOrUpdateTutorForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/tutores")).andExpect(status().isOk());
		mockMvc.perform(get("/tutores/new")).andExpect(status().isOk());
		mockMvc.perform(get("/tutores/"+TEST_TUTOR_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/tutores/"+TEST_TUTOR_ID+"/edit")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/tutores/new")
							.with(csrf())
							.param("nombre", "Juanra")
							.param("apellidos", "Ostos")
							.param("email", "hulalalala@alum.us.es")
							.param("password", "Estoesuna666@muyrara")
							.param("image", "/resources/images/pets.png"))
		.andExpect(status().isOk()).andExpect(model().hasNoErrors());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormFailure() throws Exception {
		mockMvc.perform(post("/tutores/new")
					.with(csrf())
					.param("nombre", "Juanra")
					.param("apellidos", "Ostos")
					.param("email", "hulalalala@gmail.es"))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testFindProcessTutor() throws Exception {
//		mockMvc.perform(get("/tutores/")).andExpect(status().isOk())
//				.andExpect(model().attributeExists("nombre", "Pepe"));
//	}
	
}
