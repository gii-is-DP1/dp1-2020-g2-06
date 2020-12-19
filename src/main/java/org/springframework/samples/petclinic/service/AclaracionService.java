package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.repository.AclaracionRepository;
import org.springframework.stereotype.Service;

@Service
public class AclaracionService {
	
	@Autowired
	private AclaracionRepository aclaracionRepository;
	
	public Collection<Aclaracion> findAll(){
		return aclaracionRepository.findAll();
	}
	
	public Optional<Aclaracion> findById(int id){
		return aclaracionRepository.findById(id);
	}
	
	public void save(Aclaracion aclaracion) {
		aclaracionRepository.save(aclaracion);
	}
	
	public Collection<Aclaracion> findAllByTutor(int id){
		return aclaracionRepository.findAllByTutor(id);
	}
	
	public Collection<Aclaracion> findAllByProblema(int id){
		return aclaracionRepository.findAllByProblema(id);
	}

}
