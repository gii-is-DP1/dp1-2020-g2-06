package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ComentarioControllerTests {
	
	private static final int TEST_ALUMNO_ID = 0;
	private static final int TEST_ENVIO_ID = 0;
	
	@Autowired
    private WebApplicationContext context; 
	
	private MockMvc mockMvc;
	
	
	@MockBean
	private AuthService authService;
	
	private Alumno alumno;
	
	private Envio envio;
	
	private Problema problema;

	
	@MockBean
	private EnvioService envioService;
	
	@MockBean
	private AlumnoService alumnoService;

	
	@BeforeEach
		void setup() {
		mockMvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(SecurityMockMvcConfigurers.springSecurity())
		          .build();
		
		
			alumno = new Alumno();
			alumno.setId(TEST_ALUMNO_ID);
			
			problema = new Problema();
			problema.setId(1);

			Temporada aux = new Temporada();
			aux.setNombre("Invierno");
			
			envio = new Envio();
			envio.setId(TEST_ENVIO_ID);
			envio.setAlumno(alumno);
			envio.setCodigoPath("codes/prueba.java");
			envio.setFecha(LocalDateTime.of(2020, 8, 21, 11, 13));
			envio.setIdJudge(18);
			envio.setListaComentarios(new ArrayList<Comentario>());
			envio.setProblema(problema);
			envio.setResolucion("AC");
			envio.setSeason(aux);
			envio.setSeasonYear(2020);
			
	}
	
	//Historia de usuario 19 caso positivo
	@WithMockUser(username="alexis@alum.us.es",authorities="alumno")
    @Test
    void testProcessCreationFormSuccessAsAlumno() throws Exception {
		given(envioService.findById(TEST_ALUMNO_ID)).willReturn(Optional.of(envio));
		given(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(alumno));
		mockMvc.perform(post("/comentarios/new")
						.with(csrf())
						.param("texto", "¡Buena solución!")
						.param("idEnvio", "0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/envios/0"));
	}
	
	//Historia de usuario 19 caso negativo
		@WithMockUser(username="alexis@alum.us.es",authorities="alumno")
	    @Test
	    void testProcessCreationFormFailure() throws Exception {
			given(envioService.findById(TEST_ALUMNO_ID)).willReturn(Optional.of(envio));
			given(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(alumno));
			mockMvc.perform(post("/comentarios/new")
							.with(csrf())
							.param("texto", "")
							.param("idEnvio", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("/envios/0"));
		}
	
	//Historia de usuario 19 caso negativo
	@WithMockUser(value = "spring",authorities={"tutor"})
    @Test
    void testProcessCreationFormNotAsAlumno() throws Exception {
		given(envioService.findById(TEST_ALUMNO_ID)).willReturn(Optional.of(envio));
		given(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(alumno));
		mockMvc.perform(post("/comentarios/new")
						.with(csrf())
						.param("texto", "Muy buen envio")
						.param("idEnvio", "0"))
			.andExpect(status().isForbidden());
	}
	
}
