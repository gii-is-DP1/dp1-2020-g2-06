package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.repository.LogroRepository;
import org.springframework.stereotype.Service;

@Service
public class LogroService {

	@Autowired
	LogroRepository logroRepository;
	
	@Autowired
	EnvioService envioService;
	
	public Collection<Logro> findAll(){
		return logroRepository.findAll();
	}
	
	public Optional<Logro> findById(int id){
		return logroRepository.findById(id);
	}
	
	public Collection<Logro> obtenerLogros(Alumno alumno) {
		Collection<Envio> envios = envioService.findAllByAlumno(alumno.getId());
		Collection<Envio> enviosAc = envioService.findAllByAlumnoAc(alumno.getId());
		Collection<Logro> result = new ArrayList<Logro>();
		if(envios.size()>=1000) {
			result.add(logroRepository.findById(4).get());
			result.add(logroRepository.findById(3).get());
			result.add(logroRepository.findById(2).get());
			result.add(logroRepository.findById(1).get());
			result.add(logroRepository.findById(0).get());
		}else if(envios.size()>=500) {
			result.add(logroRepository.findById(3).get());
			result.add(logroRepository.findById(2).get());
			result.add(logroRepository.findById(1).get());
			result.add(logroRepository.findById(0).get());
		}else if(envios.size()>=100) {
			result.add(logroRepository.findById(2).get());
			result.add(logroRepository.findById(1).get());
			result.add(logroRepository.findById(0).get());
		}else if(envios.size()>=50){
			result.add(logroRepository.findById(1).get());
			result.add(logroRepository.findById(0).get());
		}else if(envios.size()>=10) {
			result.add(logroRepository.findById(0).get());
		}
		
		if(enviosAc.size()>=100) {
			result.add(logroRepository.findById(8).get());
			result.add(logroRepository.findById(7).get());
			result.add(logroRepository.findById(6).get());
			result.add(logroRepository.findById(5).get());
		}
		
		return result;
		
	}

}
