package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
public class CreadorControllerTests {

	private static final int TEST_CREADOR_ID = 0;
	
	private MockMvc mockMvc;

	 @Autowired
	    private WebApplicationContext context;
	 
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
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
		
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
	
	//Historia de usuario 26 caso positivo
	@WithMockUser(value = "spring", authorities= {"administrador"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/creadores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("creador"))
		.andExpect(view().name("creadores/createOrUpdateCreadorForm"));
	}
	
	//Historia de usuario 26 y 28 casos positivos
	@WithMockUser(value = "spring", authorities= {"creador", "administrador"})
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/creadores")).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/new")).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/"+TEST_CREADOR_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/creadores/"+TEST_CREADOR_ID+"/edit")).andExpect(status().isOk());
	}
	
	//Historia de usuario 26 caso positivo
	@WithMockUser(username = "DBGames@us.es", authorities= {"creador", "administrador"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/creadores/new")
				.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
				.with(csrf())
				.param("apellidos", "Brincau Cano")
				.param("email", "DBGames@us.es")
				.param("nombre", "David")
				.param("pass", "1234AbCd@"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/creadores"));
		
	}
	
	//Historia de usuario 28 y 1 caso positivo
	@WithMockUser(username = "DBGames@us.es", authorities= {"creador", "administrador"})
	@Test
	void testProcessUpdateCreadorFormSuccess() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/creadores/0/edit")
				.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
				.with(csrf())
				.param("apellidos", "Brincau Cano")
				.param("email", "DBGamesss@us.es")
				.param("nombre", "Daniel")
				.param("pass", "4321AbCd@"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(status().isOk());
	}
	
	//Historia de usuario 26 caso negativo
	@WithMockUser(username = "DBGames@us.es", authorities= {"creador", "administrador"})
	@Test
	void testProcessCreationFormFailure() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/creadores/new")
				.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
				.with(csrf())
				.param("nombre", "David")
				.param("pass", "1234AbCd@"))
				.andExpect(status().isOk())
				.andExpect(view().name("creadores/createOrUpdateCreadorForm"));
	}

	//Historia de usuario 1, 26 y 28 caso negativo
    @WithMockUser(authorities = {"alumno", "tutor"})
	@Test
	void testProcessCreateAndUpdateCreadorFormAuthErrors() throws Exception {
    	byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/creadores/new")
				.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
				.with(csrf())
				.param("apellidos", "Brincau Cano")
				.param("email", "DBGames@us.es")
				.param("nombre", "Daniel")
				.param("pass", "1234AbCd@"))
				.andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/creadores/0/edit")
				.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
				.with(csrf())
				.param("apellidos", "Brincau Cano")
				.param("email", "DBGamesss@us.es")
				.param("nombre", "Daniel")
				.param("pass", "4321AbCd@"))
				.andExpect(status().isForbidden());
		
	}

}
