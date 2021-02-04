package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.domjudge.ProblemResponse;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.service.CreadorService;
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
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ProblemaControllerTests {
	
	@Autowired
    private WebApplicationContext context; 
	
	private MockMvc mockMvc;
	
	@MockBean
	private ProblemaService problemaService;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private EnvioService envioService;
	
	@MockBean
	private JudgeService judgeService;
	
	@MockBean
	private CreadorService creadorService;
	
	
	@BeforeEach
	void setup() throws InterruptedException, IOException {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
		
		
	Problema problema  = new Problema();
	problema.setId(6);
	problema.setCreador(new Creador());
	problema.setIdJudge(6);
	problema.setName("Hello friend");
	problema.setDificultad("6");
	problema.setPuntuacion(6);
	problema.setDescripcion("This is my description");
	problema.setCasos_prueba("2 3 2 3");
	problema.setSalida_esperada("Out");
	problema.setFechaPublicacion(LocalDate.now());
	problema.setSeason(new Temporada());
	problema.setSeasonYear(2021);
	
	//Consultado en uno de los controladores
	problema.getCreador().setEmail("davbrican@us.es");
	problema.getCreador().setId(4);
	
	Creador cr = new Creador();
	cr.setEmail("davbrican@us.es");
	cr.setId(4);
	
	Map<String, Long> resoluciones = new HashMap<String,Long>();
	resoluciones.put("AC", 2L);
	
	ProblemResponse pr = new ProblemResponse();
	List<Integer> ls = new ArrayList<>();
	ls.add(1);
	pr.setProblemIds(ls);
	
	//Mocks de consultas a servicios
	given(this.envioService.resolucionProblema(Mockito.anyInt())).willReturn(resoluciones);
	given(this.problemaService.findById(6)).willReturn(Optional.of(problema));
	given(this.problemaService.ProblemasVigentes()).willReturn(new ArrayList<>());
	given(this.creadorService.findByEmail(Mockito.anyString())).willReturn(Optional.of(cr));
	given(this.judgeService.addProblem(Mockito.anyInt(),Mockito.anyString())).willReturn(pr);

	}

	@WithMockUser(authorities="alumno")
	@Test
	void testShowProblemas() throws Exception {
		mockMvc.perform(get("/problemas")).andExpect(status().isOk())
		.andExpect(view().name("problemas/problemasList"));
	}
	
	@WithMockUser(authorities="alumno")
	@Test
	void testShowProblemaDetails() throws Exception {
		mockMvc.perform(get("/problemas/6")).andExpect(status().isOk())
		.andExpect(view().name("problemas/problemaDetails"));
	}
	
	@WithMockUser(authorities="alumno")
	@Test
	void testDoNotShowNoExistingProblemaDetails() throws Exception {
		mockMvc.perform(get("/problemas/1")).andExpect(status().isOk())
		.andExpect(view().name("problemas/problemasList"));
	}
	
	@WithMockUser(authorities="creador")
	@Test
	void testShowProblemaInitForm() throws Exception {
		mockMvc.perform(get("/problemas/new")).andExpect(status().isOk())
		.andExpect(view().name("problemas/createOrUpdateProblemaForm"));
	}
	
	@WithMockUser(authorities="alumno")
	@Test
	void testDoNotShowProblemaInitForm() throws Exception {
		mockMvc.perform(get("/problemas/new")).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(username = "davbrican@us.es", authorities="creador")
	@Test
	void testShowProblemaUpdateForm() throws Exception {
		mockMvc.perform(get("/problemas/6/edit")).andExpect(status().isOk())
		.andExpect(view().name("problemas/createOrUpdateProblemaForm"));
	}
	
	
	@WithMockUser(value = "davbrican@us.es", authorities= "creador")
	@Test
	void testProblemaCreate() throws Exception {
		byte[] somebytes = { 1, 5, 5, 0, 1, 0, 5 };
		mockMvc.perform(MockMvcRequestBuilders.multipart("/problemas/new")
							.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
							.file(new MockMultipartFile("zipo","file.zip", "application/zip", somebytes))
							.with(csrf())
							.param("name", "Problema444")
							.param("puntuacion", "4")
							.param("dificultad", "4")
							.param("descripcion", "Esto es un problema de prueba")
							.param("casos_prueba", "1 2 34 2")
							.param("salida_esperada", "1 2 34 2")
							.param("season", "2")
							.param("seasonYear", "2020")
							)
		.andExpect(status().isOk())
		//.andExpect(model().attributeExists("problema"))
		.andExpect(view().name("problemas/problemasList"));
	}
	
	@WithMockUser(authorities="creador")
	@Test
	void testProblemaUpdate() throws Exception {
		byte[] somebytes = {1,2,4,5};
		mockMvc.perform(MockMvcRequestBuilders.multipart("/problemas/6/edit")
							.file(new MockMultipartFile("image","file.jpg", "image/jpeg", somebytes))
							.file(new MockMultipartFile("zipo","file.zip", "application/zip", somebytes))
//							.requestAttr("season", temporada)
							.with(csrf())
							.param("name", "Problema444")
							.param("puntuacion", "4")
							.param("dificultad", "4")
							.param("descripcion", "Esto es un problema de prueba")
							.param("casos_prueba", "1 2 34 2")
							.param("salida_esperada", "1 2 34 2")
							.param("seasonYear", "2020")
							.param("imagen", "images/prueba")
							.param("season", "1")
							
							)
		.andExpect(status().isOk())
		//.andExpect(model().attributeExists("problema"))
		.andExpect(view().name("problemas/problemasList"));
	}

	
}
