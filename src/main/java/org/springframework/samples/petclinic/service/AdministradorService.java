package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.AdministradorRepository;
import org.springframework.samples.petclinic.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
	
	@Autowired
	AdministradorRepository administradorRepo;
	
	
	public Collection<Administrador> findAll(){
		return administradorRepo.findAll();
	}
	
	public Optional<Administrador> findById(int id){
		return administradorRepo.findById(id);
	}
	
	public void save(@Valid Administrador administrador) {
		administradorRepo.save(administrador);
	}

	public Optional<Administrador> findByEmail(String email) {
		return administradorRepo.findByEmail(email);
	}
	
	
}
