package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.repository.EnvioRepository;
import org.springframework.stereotype.Service;

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
	
	public Slice<Envio> findAllByAlumnoPage(Pageable pageable,int id){
		return envioRepository.findAllByAlumnoPage(pageable,id);
	}
	
	public Collection<Integer> findAllByAlumnoAc(int id){
		return envioRepository.findAllByAlumnoAc(id);
	}
	
	public Collection<Integer> findAllByAlumnoWa(int id){
		return envioRepository.findAllByAlumnoWa(id);
	}
	
	public Collection<Envio> findAllByProblema(int id){
		return envioRepository.findAllByProblema(id);
	}
	
	public Slice<Envio> findAllByProblemaPage(Pageable pageable,int id){
		return envioRepository.findAllByProblemaPage(pageable,id);
	}
	
	public Map<String, Long> resolucionProblema(int id){
		Map<String, Long> result = envioRepository.findAllByProblema(id).stream().collect(Collectors.groupingBy(Envio::getResolucion, Collectors.counting()));
		if(envioRepository.findAllByProblemaAC(id).size()!=0) {
			result.put("AC", (long) envioRepository.findAllByProblemaAC(id).size());
		}
		return result;
	}


}
