package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AclaracionService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.NormaWebService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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

	/*Este metodo se ejecuta antes de cada test, para inicializar mockeando los nuevos datos con los que comprobaremos todo.
	 * Aquí se crea un tutor, con una norma asociada y se emplea los given
	 * para simular datos.*/
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
	
	//Historia de usuario 29 y 30 caso positivo
	/*Con mockMvc.perform _____  .andExpect(status().isOk());  comprobamos que esas vistas son accesibles siendo un tutor, es decir, que un tutor puede solicitar
	 * accesoa  los formularios de creacion, edicion y borrado de normas.*/
	@WithMockUser(username = "davbrican@us.es", authorities = "tutor")
	@Test
	void testcomprobarUrls() throws Exception {
		mockMvc.perform(get("/normasWeb")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/0/edit")).andExpect(status().isOk());
		mockMvc.perform(get("/normasWeb/0/delete")).andExpect(status().isOk());
	}

	//Historia de usuario 29 caso positivo
	/*Aquí comprobamos que el formulario de creacion y edicion es accesible para un tutor*/
	@WithMockUser(value = "spring", authorities = "tutor")
	@Test
	void testInitCreateFormSuccess() throws Exception{
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().isOk())
		.andExpect(view().name("normasWeb/createOrUpdateNormaWebForm"));
	}

	//Historia de usuario 29 caso negativo
	/*Aquí comprobamos que el formulario de creacion y edicion no es accesible para un alumno, creador ni administrador*/
	@WithMockUser(value = "spring", authorities = {"alumno", "creador", "administrador"})
	@Test
	void testInitCreateFormFailure() throws Exception{
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().is4xxClientError());
	}

	//Historia de usuario 29 caso positivo
	/*Se llama al metodo post /normasWeb/new y simulando los parametros de entrada, comprobamos que se hace una redirecciona  la nueva página, señal de que se ha insertado isn errores.*/
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

	//Historia de usuario 30 caso negativo
	/*Se llama al metodo post /normasWeb/new y simulando los parametros de entrada, observamos que da un codigo 200 pero se envia de nuevoa  la vista del formulario
	 * debido a que el parametro descripcion no puede estar vacio.*/
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

	//Historia de usuario 30 caso positivo
	/*Tratamos de editar una norma de forma correcta con un tutor, y este nos devuelve a la lista de normas tras comprobar que no hay fallos en la insercion*/
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

	//Historia de usuario 30 caso negativo
	/*Tratamos de editar una norma de forma correcta pero registrados como uno de los usuarios sin acceso a este privilegio, y nos devuelve un error 403 isFrobidden*/
	@WithMockUser(value = "spring", authorities = {"alumno", "creador", "administrador"})
	@Test
	void testEditNormaWebFailure()throws Exception{
		mockMvc.perform(post("/normasWeb/"+TEST_NORMA_ID+"/edit")
						.with(csrf())
						.param("name", "ATENCION")
						.param("descripcion", "Hola"))
		.andExpect(status().isForbidden());
	}

	//Historia de usuario 31 caso negativo
	/*Accedemos al borrado de una norma, introduciendo bien tanto la id como todos los parámetros necesarios, y siendo un tutor, y por ello nos redirige a la pagina tomada como la pagina de 
	 * exito para este caso */
	@WithMockUser(username = "davbrican@us.es", authorities = "tutor")
	@Test
	void testDeleteNormaWebSucces()throws Exception{
		mockMvc.perform(get("/normasWeb/"+TEST_NORMA_ID+"/delete")).andExpect(status().isOk())
		.andExpect(view().name("normasWeb/normasWebList"));;
	}
	
	//Historia de usuario 31 caso negativo
	/*Intentamos borrar correctamente una nroma pero con  la sesion iniciada como alumno, creador, y administrador, y nos 
	 * da error de verificacion, un error del grupo 4xx ya que tenemos prohibido ela cceso del lado del cliente.*/
	@WithMockUser(authorities = {"alumno","administrador","creador"})
	@Test
	void testDeleteNormaWebFailure()throws Exception{
		mockMvc.perform(get("/normasWeb/"+TEST_NORMA_ID+"/delete")).andExpect(status().is4xxClientError());
	}
}
