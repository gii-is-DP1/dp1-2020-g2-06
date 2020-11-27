package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.repository.NormaWebRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NormaWebService {

	@Autowired
	private NormaWebRepository normaWebRepository;
	
	@Transactional
	public int normaWebCount() {
		return (int) normaWebRepository.count();
	}
	
	@Transactional
	public Collection<NormaWeb> findAll() {
		return normaWebRepository.findAll();
	}
	
	public Optional<NormaWeb> findById(int id){
		return normaWebRepository.findById(id);
	}
	
	public void delete(NormaWeb normaWeb) {
		normaWebRepository.deleteById(normaWeb.getId());
	}
	
	@Transactional(rollbackFor = ConstraintViolationException.class)
	public void saveNormaWeb(@Valid NormaWeb normaWeb)  {
		//creating normaWeb
			normaWebRepository.save(normaWeb);
	}		

}
