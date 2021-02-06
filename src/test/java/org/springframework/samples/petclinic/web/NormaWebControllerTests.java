package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NormaWebService;
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
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
public class NormaWebControllerTests {

	
	private static final int TEST_NORMA_ID = 0;
	
	
	private MockMvc mockMvc;
	
	 @Autowired
	    private WebApplicationContext context;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private NormaWebService normaWebService;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private ProblemaService problemaService;
	
	@MockBean
	private AclaracionService aclaracionService;
	
	private NormaWeb normaWeb;

	
	@BeforeEach
		void setup() {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
		
			Tutor tutor = new Tutor();
			tutor.setEmail("davbrican@us.es");
			tutor.setId(0);
			tutor.setApellidos("Brincau");
			tutor.setEnabled(true);
			tutor.setNombre("David");
			tutor.setPass("123SADgfh@");
			tutor.setImagen("/static/resources/images/noticias/2020122317827911000000.jpg");
			Optional<NormaWeb> n = Optional.empty();
			
			normaWeb = new NormaWeb();
			normaWeb.setId(TEST_NORMA_ID);
			normaWeb.setAutor(tutor);
			normaWeb.setName("RESPETAR");
			normaWeb.setDescripcion("Respeta a todo el mundo por igual");
			n = Optional.of(normaWeb);	
			
			given(this.normaWebService.findAll()).willReturn(new ArrayList<NormaWeb>());
			given(this.normaWebService.findById(TEST_NORMA_ID)).willReturn(n);
			given(this.tutorService.findAll()).willReturn(new ArrayList<Tutor>());
			given(this.tutorService.findByEmail(Mockito.anyString())).willReturn(Optional.of(tutor));
			
	}
	
	
	@WithMockUser(username = "davbrican@us.es", authorities = "tutor")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/normasWeb")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/0/edit")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/0/delete")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testInitCreateFormSuccess() throws Exception{
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().isOk())
		.andExpect(view().name("normasWeb/createOrUpdateNormaWebForm"));
	}

	@WithMockUser(value = "spring", authorities = {"alumno", "creador", "administrador"})
	@Test
	void testInitCreateFormFailure() throws Exception{
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testProcessCreationFormSuccess()throws Exception{
		mockMvc.perform(post("/normasWeb/new")
						.with(csrf())
						.param("name", "ATENCION")
						.param("descripcion", "Estar atentos durante las explicaciones"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/normasWeb/"));
	}

	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testProcessCreationFormFailure()throws Exception{
		mockMvc.perform(post("/normasWeb/new")
						.with(csrf())
						.param("name", "ATENCION")
						.param("descripcion", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("normasWeb/createOrUpdateNormaWebForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testEditNormaWebSucces()throws Exception{
		mockMvc.perform(post("/normasWeb/"+TEST_NORMA_ID+"/edit")
						.with(csrf())
						.param("name", "ATENCION")
						.param("descripcion", "Hola buenas tardes."))
		.andExpect(status().isOk())
		.andExpect(view().name("normasWeb/normasWebList"));
	}
	
	@WithMockUser(value = "spring", authorities = {"alumno", "creador", "administrador"})
	@Test
	void testEditNormaWebFailure()throws Exception{
		mockMvc.perform(post("/normasWeb/"+TEST_NORMA_ID+"/edit")
						.with(csrf())
						.param("name", "ATENCION")
						.param("descripcion", "Hola"))
		.andExpect(status().isForbidden());
	}
	
	@WithMockUser(username = "davbrican@us.es", authorities = "tutor")
	@Test
	void testDeleteNormaWebSucces()throws Exception{
		mockMvc.perform(get("/normasWeb/"+TEST_NORMA_ID+"/delete")).andExpect(status().isOk())
		.andExpect(view().name("normasWeb/normasWebList"));;
	}
	@WithMockUser(authorities = {"alumno","administrador","creador"})
	@Test
	void testDeleteNormaWebFailure()throws Exception{
		mockMvc.perform(get("/normasWeb/"+TEST_NORMA_ID+"/delete")).andExpect(status().is4xxClientError());
	}
}
