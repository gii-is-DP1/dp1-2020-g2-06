package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.PreguntaTutor;
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
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
public class ArticuloControllerTests {

	
	private static final int TEST_ARTICULO_ID = 0;
	
	
	private MockMvc mockMvc;
	
	 @Autowired
	    private WebApplicationContext context;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private ArticuloService articuloService;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private ProblemaService problemaService;
	
	@MockBean
	private AclaracionService aclaracionService;
	
	private Articulo articulo;

	
	@BeforeEach
		void setup() {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
			Set<Tutor> autores = new HashSet<Tutor>();
			Tutor tutor = new Tutor();
			tutor.setEmail("vicgragil@us.es");
			Optional <Articulo> n = Optional.empty();
			autores.add(tutor);
			articulo = new Articulo();
			articulo.setId(TEST_ARTICULO_ID);
			articulo.setAutores(autores);
			articulo.setFechaPublicacion(LocalDate.now());
			articulo.setImagen("/resources/images/pets.png");
			articulo.setName("Esto es una prueba");
			articulo.setTexto("Esto es un texto de prueba");
			n = Optional.of(articulo);	
			
			given(this.articuloService.findAll()).willReturn(new ArrayList<Articulo>());
			given(this.articuloService.findById(TEST_ARTICULO_ID)).willReturn(n);
			given(this.articuloService.findArticulosByTutor(TEST_ARTICULO_ID)).willReturn(new ArrayList<Articulo>());
			given(this.tutorService.findAll()).willReturn(new ArrayList<Tutor>());
			given(this.tutorService.findByEmail(Mockito.anyString())).willReturn(Optional.of(tutor));
			
	}
	
	//HU-6 Ver Comunidad de Programación Competitiva
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/articulos")).andExpect(status().isOk());
		mockMvc.perform(get("/articulos/new")).andExpect(status().isOk());
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID+"/edit")).andExpect(status().isOk());
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID+"/delete")).andExpect(status().isOk());
	}
	//HU-6 Ver Comunidad de Programación Competitiva
	@WithMockUser(value = "spring", authorities = "alumno")
	@Test
	void testcomprobarUrls2() throws Exception {
		mockMvc.perform(get("/articulos")).andExpect(status().isOk());
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID)).andExpect(status().isOk());
	}
	//HU-4 Crear Artículos para la web 
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testInitCreateFormSuccess() throws Exception{
		mockMvc.perform(get("/articulos/new")).andExpect(status().isOk())
		.andExpect(view().name("articulos/createOrUpdateArticuloForm"));
	}
	//Caso negativo HU-4 Crear Artículos para la web 
	@WithMockUser(value = "spring", authorities = {"alumno", "creador", "administrador"})
	@Test
	void testInitCreateFormFailure() throws Exception{
		mockMvc.perform(get("/articulos/new")).andExpect(status().is4xxClientError());
	}
	//Caso positivo HU-4 Crear Artículos para la web 
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testProcessCreationFormSuccess()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/articulos/new")
							.file(new MockMultipartFile("image","resources/images/articulos/2020122317841918000000.jpg", "image/jpeg", somebytes))
							.with(csrf())
							.param("autores", "3")
							.param("_autores", "on")
							.param("name", "Arboles en la programación competitiva")
							.param("fechaPublicacion", "2021/01/05")
							.param("texto", "Lorem Ipsum is simply dummy text of the printing..."))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/articulos"));
	}
	//Caso negativo HU-4 Crear Artículos para la web 
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testProcessCreationFormFailure()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/articulos/new")
							.file(new MockMultipartFile("image","resources/images/articulos/2020122317841918000000.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("autores", "3")
							.param("_autores", "on")
							.param("fechaPublicacion", "2021/01/05"))
		.andExpect(status().isOk())
		.andExpect(view().name("articulos/createOrUpdateArticuloForm"));
	}
	
	//Caso positivo HU-5 Editar Artículos de la web
	@WithMockUser(username = "vicgragil@us.es", authorities = "tutor")
	@Test
	void testEditArticuloSucces()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/articulos/"+TEST_ARTICULO_ID+"/edit")
				.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
				.with(csrf())
				.param("autores", "3")
				.param("autores", "4")
				.param("autores", "5")
				.param("_autores", "on")
				.param("name", "Artículo sobre árboles")
				.param("texto","Texto de prueba" ))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "El artículo se ha actualizado con éxito"));
	}	
	//Caso negativo HU-5 Editar Artículos de la web
	@WithMockUser(authorities = {"alumno","administrador","creador"})
	@Test
	void testEditArticuloFailureAuth()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/articulos/"+TEST_ARTICULO_ID+"/edit")
				.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
				.with(csrf())
				.param("autores", "3")
				.param("autores", "4")
				.param("autores", "5")
				.param("_autores", "on"))
				.andExpect(status().is4xxClientError());

	}
	//Caso positivo HU-33 Borrado de Artículos
	@WithMockUser(username = "vicgragil@us.es", authorities = "tutor")
	@Test
	void testDeleteArticuloSucces()throws Exception{
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID+"/delete")).andExpect(status().isOk())
		.andExpect(view().name("/articulos/articulosList"));;
	}
	//Caso negativo HU-33 Borrado de Artículos
	@WithMockUser(authorities = {"alumno","administrador","creador"})
	@Test
	void testDeleteArticuloFailure()throws Exception{
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID+"/delete")).andExpect(status().is4xxClientError());
		
	//Caso negativo HU-33 Borrado de Artículos
	}
	@WithMockUser(authorities = "tutor")
	@Test
	void testDeleteArticuloFailure2()throws Exception{
		mockMvc.perform(get("/articulos/"+500+"/delete")).andExpect(status().isOk())
		.andExpect(model().attribute("message", "No podemos encontrar el articulo que intenta borrar"))
		.andExpect(view().name("/articulos/articulosList"));

	}
	

	
}
