package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/error").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/aclaraciones/new").hasAuthority("tutor")
				.antMatchers("/administradores").hasAuthority("administrador")
				.antMatchers("/alumnos/*").permitAll()
				.antMatchers("/alumnos/{id}/edit").hasAnyAuthority("alumno","administrador")
				.antMatchers("/articulos/").permitAll()
				.antMatchers("/articulos/new").hasAuthority("tutor")
				.antMatchers("/articulos/{id}/edit").hasAuthority("tutor")
				.antMatchers("/articulos/{id}/delete").hasAuthority("tutor")
				.antMatchers("/comentarios/new").hasAuthority("alumno")
				.antMatchers("/creadores/").permitAll()
				.antMatchers("/creadores/new").hasAuthority("administrador")
				.antMatchers("/creadores/{id}/edit").hasAuthority("creador")
				.antMatchers("/envios/{id}").permitAll()
				.antMatchers("/envios/send/{problema}").hasAuthority("alumno")
				.antMatchers("/normasWeb/").permitAll()
				.antMatchers("/normasWeb/new").hasAuthority("tutor")
				.antMatchers("/normasWeb/{id}/edit").hasAuthority("tutor")
				.antMatchers("/normasWeb/{id}/delete").hasAuthority("tutor")
				.antMatchers("/noticias/").permitAll()
				.antMatchers("/noticias/new").hasAuthority("tutor")
				.antMatchers("/noticias/{id}/edit").hasAuthority("tutor")
				.antMatchers("/noticias/{id}/delete").hasAuthority("tutor")
				.antMatchers("/preguntatutor/new").hasAuthority("alumno")
				.antMatchers("/preguntatutor/answer").hasAuthority("tutor")
				.antMatchers("/problemas/").permitAll()
				.antMatchers("/problemas/new").hasAuthority("creador")
				.antMatchers("/problemas/{id}/edit").hasAnyAuthority("creador")
				.antMatchers("/problemas/{id}/delete").hasAnyAuthority("creador")
				.antMatchers("/foro").permitAll()
				.antMatchers("/foro/new").hasAuthority("alumno")
				.antMatchers("/tutores/").permitAll()
				.antMatchers("/tutores/new").hasAuthority("administrador")
				.antMatchers("/tutores/{id}/edit").hasAnyAuthority("tutor","administrador")
				.antMatchers("/api/**").permitAll()
				.antMatchers("perfil").permitAll()
				.antMatchers("/welcome").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/owners/**").hasAnyAuthority("owner","admin")				
				.antMatchers("/vets/**").authenticated()
				.antMatchers("/**").permitAll() //Configurar como denyAll
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select * from (select * from (select email as user,pass as password,enabled from alumnos) union "
	       + "(select email as user,pass as password,enabled from tutores) union "
	       + "(select email as user,pass as password,enabled from creadores) union "
	       + "(select email as user,pass as password,enabled from administradores)) where user = ?")
	      .authoritiesByUsernameQuery(
	       "select * from (select * from (select a.email, authority from alumnos as a right join auths b on a.id = b.id_alumno) union "
	       + "(select a.email, authority from creadores as a right join auths b on a.id = b.id_creador) union "
	       + "(select a.email, authority from tutores as a right join auths b on a.id = b.id_tutor) union "
	       + "(select a.email, authority from administradores as a right join auths b on a.id = b.id_administrador)) where email = ?")      
	      .passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder  passwordEncoder() {	    
		return new BCryptPasswordEncoder();
	}
	
}


