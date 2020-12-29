package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PuntuacionProblema;
import org.springframework.samples.petclinic.repository.PuntuacionProblemaRepository;
import org.springframework.stereotype.Service;

@Service
public class PuntuacionProblemaService {
	
	@Autowired
	private PuntuacionProblemaRepository puntuacionProblemaRepository;
	
	public Collection<PuntuacionProblema> findAll(){
		return puntuacionProblemaRepository.findAll();
	}
	
	public Optional<PuntuacionProblema> findById(int id){
		return puntuacionProblemaRepository.findById(id);
	}
	
	public void save(PuntuacionProblema puntProb) {
		puntuacionProblemaRepository.save(puntProb);
	}
	
	public Collection<PuntuacionProblema> findAllByAlumno(int id){
		return puntuacionProblemaRepository.findAllByAlumno(id);
	}
	
	public Collection<PuntuacionProblema> findAllByProblema(int id){
		return puntuacionProblemaRepository.findAllByProblema(id);
	}
	
//	public Double averageAllByProblema(int id){
//		return puntuacionProblemaRepository.averageAllByProblema(id);
//	}

}
