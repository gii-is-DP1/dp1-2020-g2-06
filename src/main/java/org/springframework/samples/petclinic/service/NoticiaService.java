package org.springframework.samples.petclinic.service;

import java.util.Collection;

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

}
