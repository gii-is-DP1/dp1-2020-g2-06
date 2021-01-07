package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PreguntaTutor;
import org.springframework.samples.petclinic.repository.PreguntaTutorRepository;
import org.springframework.stereotype.Service;

@Service
public class PreguntaTutorService {
	
	@Autowired
	private PreguntaTutorRepository preguntaTutorRepository;
	
	public Collection<PreguntaTutor> findAll(){
		return preguntaTutorRepository.findAll();
	}
	
	public Optional<PreguntaTutor> findById(int id){
		return preguntaTutorRepository.findById(id);
	}
	
	public void save(PreguntaTutor preguntaTutor) {
		preguntaTutorRepository.save(preguntaTutor);
	}
//	public Collection<PreguntaTutor> findByAlumno(int id){
//		return preguntaTutorRepository.findAllByAlumno(id);
//	}
	public Collection<PreguntaTutor> findByProblema(int id){
		return preguntaTutorRepository.findAllByProblema(id);
	}
	
}
