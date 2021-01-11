package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
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
	
	public Logro obtenerLogroEnvio(Alumno alumno) {
		Integer envios = envioService.findAllByAlumno(alumno.getId()).size();
		Logro result = null;
		if(envios>=10 && envios<50) {
			result = logroRepository.findById(0).get();
		}else if(envios>=50 && envios<100) {
			result = logroRepository.findById(1).get();
		}else if(envios>=100 && envios<500) {
			result = logroRepository.findById(2).get();
		}else if(envios>=500 && envios<1000){
			result = logroRepository.findById(3).get();
		}else if(envios>=1000) {
			result = logroRepository.findById(4).get();
		}
		return result;
		
	}
	
	public Logro obtenerLogroAccepted(Alumno alumno) {
		Integer envios = envioService.findAllByAlumnoAc(alumno.getId()).size();
		Logro result = null;
		if(envios>=10 && envios<25) {
			result = logroRepository.findById(5).get();
		}else if(envios>=25 && envios<50) {
			result = logroRepository.findById(6).get();
		}else if(envios>=50){
			result = logroRepository.findById(7).get();
		}
		return result;
	}
	
	public Logro obtenerLogroWrong(Alumno alumno) {
		Integer envios = envioService.findAllByAlumnoWa(alumno.getId()).size();
		Logro result = null;
		if(envios>=10 && envios<25) {
			result = logroRepository.findById(5).get();
		}else if(envios>=25 && envios<50) {
			result = logroRepository.findById(6).get();
		}else if(envios>=50){
			result = logroRepository.findById(7).get();
		}
		return result;
	}
}
