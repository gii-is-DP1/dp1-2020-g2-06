package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Noticia;
import org.springframework.samples.petclinic.repository.NoticiaRepository;
import org.springframework.stereotype.Service;

@Service
public class NoticiaService {
	
	@Autowired
	NoticiaRepository noticiaRepo;
	
	public Collection<Noticia> findAll(){
		return noticiaRepo.findAll();
	}
	
	public Optional<Noticia> findById(int id){
		return noticiaRepo.findById(id);
	}
	
	public void delete(Noticia noticia) {
		noticiaRepo.deleteById(noticia.getId());
	}
	
	public void save(@Valid Noticia noticia) {
		noticiaRepo.save(noticia);
	}
	

}
