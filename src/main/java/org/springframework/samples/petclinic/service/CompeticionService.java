package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.samples.petclinic.model.Competicion;
import org.springframework.samples.petclinic.repository.CompeticionRepository;
import org.springframework.stereotype.Service;

@Service
public class CompeticionService {
	
	ConversionService conversionService;

	@Autowired
	CompeticionRepository competicionRepository;
	
	public Collection<Competicion> findAll(){
		return competicionRepository.findAll();
	}
	
	public Optional<Competicion> findById(int id){
		return competicionRepository.findById(id);
	}
	
	public void save(@Valid Competicion competicion) {
		competicionRepository.save(competicion);
	}
	
	
	
}
