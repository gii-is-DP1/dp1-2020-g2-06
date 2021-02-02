/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Auth;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AuthRepository;
import org.springframework.samples.petclinic.repository.AuthoritiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class AuthService {

	private AuthRepository authRepository;
	private AlumnoService alumnoService;
	private TutorService tutorService;
	private CreadorService creadorService;
	private AdministradorService administradorService;

	@Autowired
	public AuthService(AuthRepository authRepository,AlumnoService alumnoService,TutorService tutorService,CreadorService creadorService,AdministradorService administradorService) {
		this.authRepository = authRepository;
		this.alumnoService = alumnoService;
		this.tutorService = tutorService;
		this.creadorService = creadorService;
		this.administradorService = administradorService;
	}

	@Transactional
	public void saveAuth(Auth auth) throws DataAccessException {
		authRepository.save(auth);
	}
	
	@Transactional
	public void saveAuthoritiesAlumno(String email, String role) throws DataAccessException {
		Auth auth = new Auth();
		Optional<Alumno> alumno = alumnoService.findByEmail(email);
		if(alumno.isPresent()) {
			auth.setAlumno(alumno.get());
			auth.setAuthority(role);
			//user.get().getAuthorities().add(authority);
			authRepository.save(auth);
		}else
			throw new DataAccessException("Email '"+email+"' not found!") {};
	}
	
	@Transactional
	public void saveAuthoritiesTutor(String email, String role) throws DataAccessException {
		Auth auth = new Auth();
		Optional<Tutor> tutor = tutorService.findByEmail(email);
		if(tutor.isPresent()) {
			auth.setTutor(tutor.get());
			auth.setAuthority(role);
			//user.get().getAuthorities().add(authority);
			authRepository.save(auth);
		}else
			throw new DataAccessException("Email '"+email+"' not found!") {};
	}
	
	@Transactional
	public void saveAuthoritiesCreador(String email, String role) throws DataAccessException {
		Auth auth = new Auth();
		Optional<Creador> creador = creadorService.findByEmail(email);
		if(creador.isPresent()) {
			auth.setCreador(creador.get());
			auth.setAuthority(role);
			//user.get().getAuthorities().add(authority);
			authRepository.save(auth);
		}else
			throw new DataAccessException("Email '"+email+"' not found!") {};
	}
	
	@Transactional
	public void saveAuthoritiesAdministrador(String email, String role) throws DataAccessException {
		Auth auth = new Auth();
		Optional<Administrador> administrador = administradorService.findByEmail(email);
		if(administrador.isPresent()) {
			auth.setAdministrador(administrador.get());
			auth.setAuthority(role);
			//user.get().getAuthorities().add(authority);
			authRepository.save(auth);
		}else
			throw new DataAccessException("Email '"+email+"' not found!") {};
	}


}
