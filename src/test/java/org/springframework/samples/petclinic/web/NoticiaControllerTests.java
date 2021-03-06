package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NoticiaService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
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
public class NoticiaControllerTests {

	
	private static final int TEST_NOTICIA_ID = 0;
	
	
	private MockMvc mockMvc;
	
	 @Autowired
	    private WebApplicationContext context;
	
	@MockBean
	private TutorService tutorService;
	
	@MockBean
	private NoticiaService noticiaService;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private ProblemaService problemaService;
	
	@MockBean
	private AclaracionService aclaracionService;
	
	private Noticia noticia;

	
	@BeforeEach
		void setup() {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
			Set<Tutor> autores = new HashSet<Tutor>();
			Tutor tutor = new Tutor();
			Optional <Noticia> n = Optional.empty();
			autores.add(tutor);
			noticia = new Noticia();
			noticia.setId(TEST_NOTICIA_ID);
			noticia.setAutor(tutor);
			noticia.setAutores(autores);
			noticia.setFechaPublicacion(LocalDate.now());
			noticia.setImagen("/resources/images/pets.png");
			noticia.setName("Esto es una prueba");
			noticia.setTexto("Esto es un texto de prueba");
			n = Optional.of(noticia);
			given(this.noticiaService.findAll()).willReturn(new ArrayList<Noticia>());
			given(this.noticiaService.findById(TEST_NOTICIA_ID)).willReturn(n);
			given(this.tutorService.findAll()).willReturn(new ArrayList<Tutor>());
			given(this.tutorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(tutor));
			
	}
	
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/noticias")).andExpect(status().isOk());
		mockMvc.perform(get("/noticias/new")).andExpect(status().isOk());
		mockMvc.perform(get("/noticias/"+TEST_NOTICIA_ID)).andExpect(status().isOk());
		mockMvc.perform(get("/noticias/"+TEST_NOTICIA_ID+"/edit")).andExpect(status().isOk());
		mockMvc.perform(get("/noticias/"+TEST_NOTICIA_ID+"/delete")).andExpect(status().isOk());
	}
	
	//HU-36 +E36
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testInitCreateFormSuccess() throws Exception{
		mockMvc.perform(get("/noticias/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("noticia")).andExpect(view().name("noticias/createOrUpdateNoticiaForm"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetNoticia() throws Exception{
		mockMvc.perform(get("/noticias/"+TEST_NOTICIA_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("noticia")).andExpect(view().name("noticias/noticiaDetails"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotGetNoticia() throws Exception {
		mockMvc.perform(get("/noticias/15")).andExpect(status().isOk()).andExpect(view().name("/noticias/noticiasList"))
		.andExpect(model().attribute("message", "No podemos encontrar la noticia"));
	}
	
	//HU-36 -E36
	@WithMockUser(value = "spring", authorities = {"alumno", "creador"})
	@Test
	void testInitCreateFormFailure() throws Exception{
		mockMvc.perform(get("/noticias/new")).andExpect(status().is4xxClientError());
		
		
	}
	
	//HU-36 +E36
	@WithMockUser(username = "jesus@us.es", authorities = "tutor")
	@Test
	void testProcessCreationFormSuccess()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/noticias/new")
							.file(new MockMultipartFile("image","resources/images/noticias/2020122317841918000000.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("autores", "3")
							.param("_autores", "on")
							.param("name", "Concurso problemas recursivos")
							.param("texto", "El proximo 10 de febrero tendrá lugar un concurso en la Universidad de Sevilla sobre problemas recursivos"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/noticias/"));
	}
	
	//HU-34 +E34
	@WithMockUser(username = "jesus@us.es", authorities = "tutor")
	@Test
	void testProcessUpdateFormSucces()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/noticias/"+TEST_NOTICIA_ID+"/edit")
							.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("autores", "3")
							.param("autores", "4")
							.param("autores", "5")
							.param("_autores", "on")
							.param("name", "Esto es el titulo")
							.param("texto", "Esto es un texto de prueba"))
		.andExpect(status().isOk())
		.andExpect(view().name("/noticias/noticiasList"));
	}
	
	//HU-34 -E34
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormFailure()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/noticias/"+TEST_NOTICIA_ID+"/edit")
							.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("autores", "3")
							.param("autores", "4")
							.param("autores", "5")
							.param("_autores", "on")
							.param("name", "Esto es el titulo")
							.param("texto", "Esto es un texto de prueba"))
		.andExpect(status().is4xxClientError());
	}
	
	//HU-36 -E36
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testProcessCreationFormFailure()throws Exception{
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/noticias/new")
							.file(new MockMultipartFile("image","file.jpg", "text/plain", somebytes))
							.with(csrf())
							.param("texto", "Esto es un texto de prueba"))
		.andExpect(status().isOk())
		.andExpect(view().name("noticias/createOrUpdateNoticiaForm"));
	}
	
	//HU-35 +E35
	@WithMockUser(username = "davbrincan@alum.us.es", authorities = "tutor")
	@Test
	void testProcessDeleteFormSuccess()throws Exception{
		mockMvc.perform(get("/noticias/11/delete")).andExpect(status().isOk()).andExpect(view().name("/noticias/noticiasList"));
	}
	
	//HU-35 -E35
	@WithMockUser(value = "davbrincan@alum.us.es")
	@Test
	void testProcessDeleteFormFailure()throws Exception{
		mockMvc.perform(delete("/noticias/"+TEST_NOTICIA_ID+"/delete")).andExpect(status().is4xxClientError());
	}
}
