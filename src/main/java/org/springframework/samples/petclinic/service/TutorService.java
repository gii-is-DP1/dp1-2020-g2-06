package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
	
	@Autowired
	TutorRepository tutoRepo;
	
	
	public Collection<Tutor> findAll(){
		return tutoRepo.findAll();
	}
	
	public Optional<Tutor> finByEmail(String email){
		return tutoRepo.findByEmail(email);
	}
	
	public void delete(Tutor tutor) {
		tutoRepo.deleteByEmail(tutor.getEmail());
	}
	
	public void save(@Valid Tutor tutor) {
		tutoRepo.save(tutor);
	}
}
