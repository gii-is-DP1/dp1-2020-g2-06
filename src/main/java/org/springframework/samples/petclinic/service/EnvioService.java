package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.repository.EnvioRepository;

public class EnvioService {
	
	@Autowired
	private EnvioRepository envioRepository;
	
	public Collection<Envio> findAll(){
		return envioRepository.findAll();
	}
	
	public Optional<Envio> findById(int id){
		return envioRepository.findById(id);
	}
	
	public void save(Envio envio) {
		envioRepository.save(envio);
	}
	
	public Collection<Envio> findAllByAlumno(int id){
		return envioRepository.findAllByAlumno(id);
	}
	
	public Collection<Envio> findAllByProblema(int id){
		return envioRepository.findAllByProblema(id);
	}

}
