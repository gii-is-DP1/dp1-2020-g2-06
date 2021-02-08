package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AuthService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class PerfilControllerTests {
	
	private static final int TEST_TUTOR_ID = 0;
	private static final int TEST_CREADOR_ID = 0;
	private static final int TEST_ALUMNO_ID = 0;
	
	@Autowired
    private WebApplicationContext context;
	
	
	private MockMvc mockMvc;

	@MockBean
	AlumnoController alumnoController;
	
	@MockBean
	TutorController tutorController;
	
	@MockBean
	TutorService tutorService;
	
	@MockBean
	CreadorService creadorService;
	
	@MockBean
	AlumnoService alumnoService;
	
	@MockBean
	CreadorController creadorController;
	
	@MockBean
	private AuthService authService;
	
	private Tutor tutor;
	
	private Alumno alumno;
	
	private Creador creador;
			
	
	@BeforeEach
    public void setup() {
    mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
    
	tutor = new Tutor();
	tutor.setId(TEST_TUTOR_ID);
	tutor.setNombre("Pepe");
	tutor.setApellidos("Alvarez Toledo");
	tutor.setEmail("codetutor@us.es");
	tutor.setImagen("/resources/images/pets.png");
	tutor.setPass("Codeus@49lsañkfjnsafsa");
	tutor.setEnabled(true);
	
	creador = new Creador();
	creador.setId(TEST_CREADOR_ID);
	creador.setNombre("Pepe");
	creador.setApellidos("Alvarez Toledo");
	creador.setEmail("codetutor@us.es");
	creador.setImagen("/resources/images/pets.png");
	creador.setPass("Codeus@49lsañkfjnsafsa");
	creador.setEnabled(true);
	
	alumno = new Alumno();
	alumno.setId(TEST_ALUMNO_ID);
	alumno.setNombre("Pepe");
	alumno.setApellidos("Alvarez Toledo");
	alumno.setEmail("codetutor@us.es");
	alumno.setImagen("/resources/images/pets.png");
	alumno.setPass("Codeus@49lsañkfjnsafsa");
	alumno.setEnabled(true);
	alumno.setCompartir(true);
    
    given(tutorService.findByEmail(any(String.class))).willReturn(Optional.of(tutor));
	given(creadorService.findByEmail(any(String.class))).willReturn(Optional.of(creador));
	given(alumnoService.findByEmail(any(String.class))).willReturn(Optional.of(alumno));
	when(alumnoController.alumnoDetails(any(Integer.class),Mockito.any(ModelMap.class))).thenReturn("alumnos/alumnoDetails");
	when(tutorController.tutorDetails(any(Integer.class),Mockito.any(ModelMap.class))).thenReturn("tutores/tutorDetails");
	when(creadorController.creadorDetails(any(Integer.class),Mockito.any(ModelMap.class))).thenReturn("creadores/creadorDetails");
    }
	
	//Caso positivo de HU-40
	@WithMockUser(value = "spring",authorities="alumno")
    @Test
    void testPerfilRedirectionAsAlumno() throws Exception {
		mockMvc.perform(get("/perfil")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("alumnos/alumnoDetails"));
	}
	
	//Caso positivo de HU-40
	@WithMockUser(value = "spring",authorities="tutor")
    @Test
    void testPerfilRedirectionAsTutor() throws Exception {
		mockMvc.perform(get("/perfil")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("tutores/tutorDetails"));
	}
	
	//Caso positivo de HU-40
	@WithMockUser(value = "spring",authorities="creador")
    @Test
    void testPerfilRedirectionAsCreador() throws Exception {
		mockMvc.perform(get("/perfil")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("creadores/creadorDetails"));
	}
	
	//Caso negativo de HU-40
	@WithMockUser(value = "spring")
    @Test
    void testPerfilRedirectionNotAuthenticated() throws Exception {
		mockMvc.perform(get("/perfil")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("/welcome"));
	}
	
	
}
