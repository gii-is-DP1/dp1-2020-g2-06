package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.PreguntaTutorService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
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
public class AlumnoControllerTests {

	private static final int TEST_ALUMNO_ID = 0;
	private static final int TEST_ALUMNO_TOKEN = 0000;
	
	private MockMvc mockMvc;
	
	 @Autowired
	    private WebApplicationContext context;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private ArticuloService articuloService;
	
	@MockBean
	private NoticiaService noticiaService;
	
	@MockBean
	private AlumnoService alumnoService;
	
	@MockBean
	private AuthService authService;
	
	@MockBean
	private CreadorService creadorService;
	
	@MockBean
	private AdministradorService administradorService;
	
	@MockBean 
	private PreguntaTutorService preguntaTutorService;
	
	@MockBean 
	private LogroService logroService;
	
	private Alumno alumno;
	
	@MockBean
	private FileService fileService;
	
	
	@BeforeEach
		void setup() {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
			List<Envio> envios = new ArrayList<Envio>();
			Envio envio = new Envio();
			envios.add(envio);
			Optional<Alumno> t = Optional.empty();
			alumno = new Alumno();
			alumno.setId(TEST_ALUMNO_ID);
			alumno.setNombre("Pepe");
			alumno.setApellidos("Alvarez Toledo");
			alumno.setEnabled(true);
			alumno.setEmail("daniel@us.es");
			alumno.setImagen("/resources/images/pets.png");
			alumno.setPass("Codeus@49lsañkfjnsafsa");
			alumno.setCompartir(true);
			alumno.setEnvios(envios);
			alumno.setConfirmation_token("tokenPrueba");
			t = Optional.of(alumno);
			
			
			
			
			
			given(this.alumnoService.findAll()).willReturn(new ArrayList<Alumno>());
			given(this.alumnoService.findById(TEST_ALUMNO_ID)).willReturn(t);
			given(this.alumnoService.problemasResueltos(TEST_ALUMNO_ID)).willReturn(new ArrayList<Problema>());
			given(this.alumnoService.problemasResueltosThisYear(TEST_ALUMNO_ID)).willReturn(new ArrayList<Problema>());
			given(this.alumnoService.problemasResueltosThisSeason(TEST_ALUMNO_ID)).willReturn(new ArrayList<Problema>());
			
						
			given(this.preguntaTutorService.findByAlumnoIdNoRespondidas(TEST_ALUMNO_ID)).willReturn(new ArrayList<PreguntaTutor>());
			given(this.preguntaTutorService.findByAlumnoIdRespondidas(TEST_ALUMNO_ID)).willReturn(new ArrayList<PreguntaTutor>());

			
			given(this.logroService.obtenerLogros(t.get())).willReturn(new ArrayList<Logro>());

	}
	

	@WithMockUser(value = "spring", authorities = "alumno")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/alumnos")).andExpect(status().isOk());
		mockMvc.perform(get("/alumnos/new")).andExpect(status().isOk());
		mockMvc.perform(get("/alumnos/"+TEST_ALUMNO_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/alumnos/"+TEST_ALUMNO_ID+"/edit")).andExpect(status().isOk());
	}
	@WithMockUser(value = "spring")
	@Test
	void testcomprobarUrls2() throws Exception {
		mockMvc.perform(get("/alumnos")).andExpect(status().isOk());
		mockMvc.perform(get("/alumnos/new")).andExpect(status().isOk());
		mockMvc.perform(get("/alumnos/"+TEST_ALUMNO_ID)).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/alumnos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("alumno"))
		.andExpect(view().name("alumnos/createOrUpdateAlumnoForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/new")
							.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
							.with(csrf())
							.param("nombre", "Victor")
							.param("apellidos", "Granero")
							.param("email", "vicgragil@alum.us.es")
							.param("pass", "Esto@@esUna4"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/alumnos/null"));
	}
	
	

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormFailure() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/new")
					.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
					.with(csrf())
					.param("nombre", "Victor")
					.param("apellidos", "Granero")
					.param("email", "aaaaaaa@gmail.es")
					.param("pass", "Estacontraseñanonoesvalida"))
		.andExpect(status().isOk())
		.andExpect(view().name("alumnos/createOrUpdateAlumnoForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "alumno")
	@Test
	void testProcessConfirmationFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/confirmation/"+TEST_ALUMNO_TOKEN))
				.andExpect(status().isOk())
				.andExpect(view().name("redirect:/alumnos/"+TEST_ALUMNO_ID));
	}
	@WithMockUser(username="daniel@us.es", authorities = "alumno")
	@Test
	void testInitProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(get("/alumnos/"+TEST_ALUMNO_ID+"/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("alumnos/createOrUpdateAlumnoForm"));
	}

	
	
	@WithMockUser(value= "spring", authorities = "alumno")
	@Test
	void testInitProcessUpdateFormFailure() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/"+500+"/edit"))
		.andExpect(status().is4xxClientError());
	}
	@WithMockUser(username="daniel@us.es", authorities = "alumno")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/0/edit")
							.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("nombre", "Daniel")
							.param("apellidos", "Granero")
							.param("email", "daniel@us.es")
							.param("pass", "Esto@@esUna4")
							)
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "Alumno actualizado con éxito"))
		.andExpect(view().name("/alumnos/alumnosList"));
	}
	@WithMockUser(username = "daniel@us.es", authorities = {"creador", "tutor"})
	@Test
	void testProcessUpdateFormFailure() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/alumnos/"+TEST_ALUMNO_ID+"/edit")
							.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("nombre", "Daniel")
							.param("apellidos", "Granero")
							.param("email", "vicgra@alum.us.es")
							.param("pass", "Esto@@esUna4")
							)
		.andExpect(status().is4xxClientError());
		
	}


	
}
