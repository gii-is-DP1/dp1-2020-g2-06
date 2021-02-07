package org.springframework.samples.petclinic.web;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ApiRestControllerTests {
	
	@Autowired
    private WebApplicationContext context; 
	
	private MockMvc mockMvc;
	

	@BeforeEach
		void setup() throws InterruptedException, IOException {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
		
		
	}
	

	@Test
	void testArticulosByTutorPage() throws Exception {
		
		mockMvc.perform(get("/api/articulos/bytutor/0"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Articulo sobre JesusApa")));

    }
	

	@Test
	void testNoticiasByTutorPage() throws Exception {
		
		mockMvc.perform(get("/api/noticias/bytutor/0"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Noticias a la carbonara")));

    }
	
	@Test
	void testTutorPage() throws Exception {
		
		mockMvc.perform(get("/api/tutores"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Aparicio Ortiz")));

    }
	
	@Test
	void testProblemasNoVigentesPage() throws Exception {
		
		mockMvc.perform(get("/api/problemasnovigentes"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("¡Hola mundo!")));

    }
	
	
	@Test
	void testCreadoresPage() throws Exception {
		
		mockMvc.perform(get("/api/PageableCreadores"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Yolanda")));

    }
	
	
	@Test
	void testEnvioByProblemaPage() throws Exception {
		
		mockMvc.perform(get("/api/envios/byproblema/0"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("AC")));

    }
	
	
	@Test
	void testEnvioByAlumnoPage() throws Exception {
		
		mockMvc.perform(get("/api/envios/byalumno/0"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("AC")));

    }
	
	@Test
	void testNoticiasPage() throws Exception {
		
		mockMvc.perform(get("/api/noticias"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Noticias crudas")));

    }
	
	@Test
	void testPublicacionesPage() throws Exception {
		
		mockMvc.perform(get("/api/PageablePublicaciones"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Rarmon")));

    }
	
	@Test
	void testArticulosPage() throws Exception {
		
		mockMvc.perform(get("/api/articulospage"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Articulo sobre JesusApa")));

    }
	
	
	@Test
	void testAlumnosPage() throws Exception {
		
		mockMvc.perform(get("/api/alumnospage"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Maria")));

    }
	
}