package org.springframework.samples.petclinic.web;
 
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
 
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.ProblemaService;
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
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class EnvioControllerTests {
 
	@Autowired
    private WebApplicationContext context; 
 
	private MockMvc mockMvc;
 
	@MockBean
	private EnvioService envioService;
 
	@MockBean
	private AlumnoService alumnoService;
 
	@MockBean
	private ProblemaService problemaService;
 
	@MockBean
	private FileService fileService;
 
	@MockBean
	private JudgeService judgeService;
 
	@MockBean
	private ProblemaController problemaController;
 
	@BeforeEach
		void setup() throws InterruptedException, IOException {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
 
		Envio envio = new Envio();
		envio.setId(0);
		envio.setIdJudge(0);
		envio.setFecha(LocalDateTime.now());
		envio.setCodigoPath("codes/prueba.java");
		envio.setResolucion("AC");
		Alumno alumno = new Alumno();
		alumno.setEmail("alebarled@alum.us.es");
		envio.setAlumno(alumno);
		envio.setProblema(new Problema());
		envio.setSeason(new Temporada());
		envio.setSeasonYear(2020);
 
		//Mock de un envio existente, con id == 0
		given(this.envioService.findById(120)).willReturn(Optional.of(envio));
		given(this.envioService.findById(0)).willReturn(Optional.of(envio));
 
		//Mock del controlador de problema al pedir una vista de todos los problemas
		given(this.problemaController.listProblemas(Mockito.any(ModelMap.class))).willReturn("problemas/problemasList");
 
 
		//Mock de la respuestas del juez al añadir envio y juzgar
		given(this.judgeService.addSubmission(Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).willReturn(3);
 
		given(this.judgeService.judge(Mockito.anyInt(), Mockito.anyInt())).willReturn("AC");
 
		//Mock de alumnoService
 
		given(this.alumnoService.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Alumno()));
 
		//Mocks de problemaController
		given(this.problemaController.problemaDetails(Mockito.anyInt(),Mockito.any(ModelMap.class))).willReturn("problemas/problemaDetails");
 
		Temporada season = new Temporada();
		season.setId(0);
		season.setNombre("PRIMAVERA");
 
		Problema p = new Problema();
		p.setIdJudge(0);
		p.setSeasonYear(2020);
		p.setSeason(season);
		given(this.problemaService.findById(Mockito.anyInt())).willReturn(Optional.of(p));
 
 
 
 
	}
 
	@WithMockUser(username="alebarled@alum.us.es" ,authorities="alumno")
	@Test
	void testShowEnvioDetails() throws Exception {
		
		// Caso positivo HU-37 Visualizar detalles de un envio
		
		mockMvc.perform(get("/envios/120")).andExpect(status().isOk()).andExpect(model().attributeExists("envio"))
		.andExpect(view().name("envios/envioDetails"));
	}
 
	@WithMockUser(authorities="alumno")
	@Test
	void testDoesNotShowEnvioDetails() throws Exception {
		
		// Caso negativo HU-37 Visualizar detalles de un envio
		
		mockMvc.perform(get("/envios/150")).andExpect(status().isOk()).andExpect(model().attributeDoesNotExist("envio"))
		.andExpect(view().name("problemas/problemasList"));
	}
 
	@WithMockUser(username="daniel@alum.us.es", authorities="alumno")
	@Test
	void testEnvioSendSuccess() throws Exception {
		
		// HU-9+E1 Envío del problema “¡Hola mundo!”
		
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/envios/send/0")
				.file(new MockMultipartFile("archivo","solucion.c", "text/plain", somebytes))
				.with(csrf()))
				.andExpect(status().is3xxRedirection());
	}
 
	@WithMockUser(authorities="alumno")
	@Test
	void testEnvioSendFailedBecauseFileEmpty() throws Exception {
		
		// HU-9-E1 Envío del problema “¡Hola mundo!”
		
		byte[] somebytes = { };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/envios/send/0")
				.file(new MockMultipartFile("archivo","archivo.zip", "text/plain", somebytes))
				.with(csrf()))
				.andExpect(view().name("problemas/problemaDetails"));
	}
 
 
	@WithMockUser(authorities="alumno")
	@Test
	void testEnvioSendFailedBecauseOfFileType() throws Exception {
		
		// caso negativo para comprobar restriccion tipo de archivo .java .c o .cpp
		
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/envios/send/0")
				.file(new MockMultipartFile("archivo","archivo.zip", "text/plain", somebytes))
				.with(csrf()))
				.andExpect(view().name("problemas/problemaDetails"));
	}
 
	@WithMockUser(authorities="tutor")
	@Test
	void testEnvioSendFailedBecauseOfUser() throws Exception {
		
		// caso negativo para comprobar restriccion de que solo los alumnos pueden realizar envios
		
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/envios/send/0")
				.file(new MockMultipartFile("archivo","archivo.cpp", "text/plain", somebytes))
				.with(csrf()))
				.andExpect(status().is4xxClientError());
	}
 
}