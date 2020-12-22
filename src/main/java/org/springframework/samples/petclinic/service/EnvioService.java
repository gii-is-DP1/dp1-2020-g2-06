package org.springframework.samples.petclinic.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.domjudge.Run;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
