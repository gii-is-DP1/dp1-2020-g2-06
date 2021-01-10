package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.repository.CreadorRepository;
import org.springframework.stereotype.Service;

@Service
public class CreadorService {
	
	@Autowired
	CreadorRepository creadorRepo;
	
	//@Autowired
	//ProblemaService problemaService
	
	
	public Collection<Creador> findAll(){
		return creadorRepo.findAll();
	}
	
	public Optional<Creador> findById(int id){
		return creadorRepo.findById(id);
	}
	
	public Integer findIdByEmail(String email){
		return creadorRepo.findIdByEmail(email);
	}
	
	public void save(@Valid Creador creador) {
		creadorRepo.save(creador);
	}

	public Optional<Creador> findByEmail(String email) {
		// TODO Auto-generated method stub
		return creadorRepo.findByEmail(email);
	}
}
