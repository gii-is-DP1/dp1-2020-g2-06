package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Publicacion;
import org.springframework.samples.petclinic.repository.PublicacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicacionService {
	
	@Autowired
	private PublicacionRepository publicacionRepository;
	
	
	public int PublicacionCount() {
		return (int) publicacionRepository.count();
	}

	public Collection<Publicacion> findAll() {
		return publicacionRepository.findAll();
	}
	
	public Optional<Publicacion> findById(int id){
		return publicacionRepository.findById(id);
	}
	
	public void delete(Publicacion publicacion) {
		publicacionRepository.deleteById(publicacion.getId());
	}
	
	@Transactional(rollbackFor = ConstraintViolationException.class)
	public void savepublicacion(@Valid Publicacion publicacion){
		publicacionRepository.save(publicacion);
	}	

}
