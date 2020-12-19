package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public Collection<Comentario> findAll(){
		return comentarioRepository.findAll();
	}
	
	public Optional<Comentario> findById(int id){
		return comentarioRepository.findById(id);
	}
	
	public void save(Comentario puntProb) {
		comentarioRepository.save(puntProb);
	}
	
	public Collection<Comentario> findAllByAlumno(int id){
		return comentarioRepository.findAllByAlumno(id);
	}
	
	public Collection<Comentario> findAllByEnvio(int id){
		return comentarioRepository.findAllByEnvio(id);
	}

}
