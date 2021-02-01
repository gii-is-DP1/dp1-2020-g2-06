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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.EnvioService;
import org.springframework.samples.petclinic.service.ProblemaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=ComentarioController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class ComentarioControllerTests {
	
	private static final int TEST_COMENTARIO_ID = 0;
	private static final int TEST_ALUMNO_ID = 0;
	private static final int TEST_ENVIO_ID = 0;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthService authService;
	
	private Aclaracion aclaracion;
	
	private Alumno alumno;
	
	private Envio envio;
	
	private Problema problema;

	
	@MockBean
	private ComentarioService comentarioService;
	
	@MockBean
	private EnvioService envioService;
	
	@MockBean
	private AlumnoService alumnoService;

	@MockBean
	private ProblemaService problemaService;
	
	@BeforeEach
		void setup() {
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
	
	@WithMockUser(value = "spring",authorities="alumno")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		given(envioService.findById(TEST_ALUMNO_ID)).willReturn(Optional.of(envio));
		given(alumnoService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).willReturn(Optional.of(alumno));
		mockMvc.perform(post("/comentarios/new")
						.with(csrf())
						.param("texto", "Muy buen envio")
						.param("idEnvio", "0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/envios/0"));
}
	
}
