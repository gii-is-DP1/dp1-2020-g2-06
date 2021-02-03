package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CreadorController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class CreadorControllerTests {

	private static final int TEST_CREADOR_ID = 0;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CreadorService creadorService;
	
	@MockBean
	private AlumnoService alumnoService;
	
	@MockBean
	private AdministradorService administradorService;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private AuthService authService;
	
	private Creador creador;
	
	
	@BeforeEach
		void setup() {
			Optional<Creador> c = Optional.empty();
			creador = new Creador();
			creador.setApellidos("Brincau");
			creador.setEmail("DBGames@us.es");
			creador.setId(TEST_CREADOR_ID);
			creador.setImagen("resources/images/creadores/2020122317244979000000.jpg");
			creador.setNombre("David");
			creador.setPass("1234AbCd@");
			creador.setEnabled(true);
			c = Optional.of(creador);
			given(this.creadorService.findById(TEST_CREADOR_ID)).willReturn(c);
		}
	
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/creadores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("creador"))
		.andExpect(view().name("creadores/createOrUpdateCreadorForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/creadores")).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/new")).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/"+TEST_CREADOR_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/"+TEST_CREADOR_ID+"/edit")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/creadores/new")
							.with(csrf())
							.param("apellidos", "Brincau Cano")
							.param("email", "DBGames@us.es")
							.param("imagen", "resources/images/creadores/2020122317244979000000.jpg")
							.param("nombre", "David")
							.param("pass", "1234AbCd@"))
		.andExpect(status().isOk()).andExpect(model().hasNoErrors());
	}
	
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testProcessCreationFormFailure() throws Exception {
		mockMvc.perform(post("/creadores/new")
					.with(csrf())
					.param("apellidos", "Brincau Cano")
					.param("email", "DBGames@youtube.com")
					.param("imagen", "resources/images/creadores/2020122317244979000000.jpg")
					.param("nombre", "David")
					.param("pass", "contraseña1"))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testProcessUpdateCreadorFormSuccess() throws Exception {
		mockMvc.perform(post("/creadores/{id}/edit", TEST_CREADOR_ID)
							.with(csrf())
							.param("apellidos", "García Villar")
							.param("nombre", "Juan")
							.param("email", "juaostrub@alum.us.es")
							.param("imagen", "resources/images/NoImage.png"))
				.andExpect(status().isOk());
	}
	
	/*
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCreadorFormHasErrors() throws Exception {
		mockMvc.perform(post("/creadores/{id}/edit", TEST_CREADOR_ID)
							.with(csrf())
							.param("apellidos", "García Villar")
							.param("nombre", "Juan")
							.param("email", "juaostrub@alum.us.es")
							.param("imagen", "resources/images/NoImage.png"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("creador"))
				.andExpect(model().attributeHasFieldErrors("creador", "email"))
				.andExpect(model().attributeHasFieldErrors("creador", "imagen"))
				.andExpect(view().name("creadores/createOrUpdateCreadorForm"));
	}
	*/

}
