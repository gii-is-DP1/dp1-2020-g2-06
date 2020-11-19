package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
