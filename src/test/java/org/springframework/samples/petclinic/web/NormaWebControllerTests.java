package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.NormaWebService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=NormaWebController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class NormaWebControllerTests {

	private static final int TEST_TUTOR_ID = 0;
	private static final int TEST_NORMA_ID = 0;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NormaWebService normaWebService;
	
	@MockBean
	private TutorService tutorService;
	
	private Tutor tutor;
	private NormaWeb norma;
	
	@BeforeEach
	void setup() {
		tutor = new Tutor();
		tutor.setId(TEST_TUTOR_ID);
		tutor.setApellidos("Brincau Cano");
		tutor.setEmail("davbrican@alum.us.es");
		tutor.setNombre("David");
		tutor.setEnabled(true);
		tutor.setPass("holaMockiMocki1u91");
		tutor.setImagen("/resources/images/creadores/2020122317244979000000.jpg");
			
		norma = new NormaWeb();
		norma.setAutor(tutor);
		norma.setDescripcion("No insultar a nadie");
		norma.setId(TEST_NORMA_ID);
		norma.setName("NO INSULTOS");
	}

	
	@WithMockUser(value = "spring", authorities= {"tutor"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/normasWeb/new")).andExpect(status().isOk()).andExpect(model().attributeExists("normaWeb"))
		.andExpect(view().name("normasWeb/createOrUpdateNormaWebForm"));
	}
	
	@WithMockUser(value = "spring",authorities={"tutor"})
    @Test
    void testProcessCreationFormSuccess() throws Exception {		
		given(normaWebService.findById(any(Integer.class))).willReturn(Optional.of(norma));
		mockMvc.perform(post("/normasWeb/new")
				.param("nombre", "NormaTest")
				.param("descripcion", "Esto es una descripción de prueba"))
		.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring",authorities={"tutor"})
    @Test
    void testProcessEditionFormSuccess() throws Exception {		
		mockMvc.perform(post("/normasWeb/{id}/edit", TEST_NORMA_ID)
				.param("nombre", "NormaTest2")
				.param("descripcion", "Esto es una descripción de prueba 2"))
		.andExpect(status().isOk())
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("normasWeb/normasWebList"));
	}
	
	
	///Ambos últimos con errores 403 Forbidden
	
}
