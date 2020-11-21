package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NormaWeb;
import org.springframework.samples.petclinic.repository.NormaWebRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NormaWebService {

	@Autowired
	private NormaWebRepository normaRepo;
	
	@Transactional
	public int normaWebCount() {
		return (int) normaRepo.count();
	}
	
	@Transactional
	public Iterable<NormaWeb> findAll() {
		return normaRepo.findAll();
	}
	
	public Optional<NormaWeb> findById(int id){
		return normaRepo.findById(id);
	}
	
	public void delete(NormaWeb normaWeb) {
		normaRepo.deleteById(normaWeb.getId());
	}
	
	public void save(@Valid NormaWeb normaWeb) {
		normaRepo.save(normaWeb);
	}
}
