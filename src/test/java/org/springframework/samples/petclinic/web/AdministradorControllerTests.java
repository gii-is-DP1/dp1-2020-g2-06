package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AuthService;
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
public class AdministradorControllerTests {
	
	@Autowired
    private WebApplicationContext context;
	
	
	private MockMvc mockMvc;
	
	@MockBean
	private AuthService authService;
			
	
	@BeforeEach
    public void setup() {
    mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
    }
	
	//Caso positivo de HU-26 Registro de Creadores y caso positivo HU-27 Registro de Tutores
	@WithMockUser(value = "spring",authorities="administrador")
    @Test
    void testPanelAdminViewAsAdministrador() throws Exception {
		mockMvc.perform(get("/administradores")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("/administradores/panelAdmin"));
	}
	
	//Caso negativo de HU-26 Registro de Creadores y caso negativo HU-27 Registro de Tutores
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormClientErrorNotAuthenticatedAsAdministrador() throws Exception {
		mockMvc.perform(get("/administradores")).andExpect(status().is4xxClientError());
	}
	
}
