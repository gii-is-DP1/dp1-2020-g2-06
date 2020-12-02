package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.repository.ProblemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemaService {

	@Autowired
	private ProblemaRepository problemaRepository;
	
	@Transactional
	public int ProblemaCount() {
		return (int) problemaRepository.count();
	}
	
	@Transactional
	public Collection<Problema> findAll() {
		return problemaRepository.findAll();
	}
	
	public Optional<Problema> findById(int id){
		return problemaRepository.findById(id);
	}
	
	public void delete(Problema problema) {
		problemaRepository.deleteById(problema.getId());
	}
	
	@Transactional(rollbackFor = ConstraintViolationException.class)
	public void saveProblema(@Valid Problema problema){
		//creating normaWeb
		problemaRepository.save(problema);
	}	
	
	public Collection<Problema> ProblemasVigentes() {
		Integer aux = LocalDate.now().getMonth().getValue()%3;
		return problemaRepository.findAll().stream().filter(x->x.getFechaPublicacion().isAfter(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue()-aux, 1)) 
													&& x.getFechaPublicacion().isBefore(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue()+(3-aux), 1))).collect(Collectors.toList());
	}
	
	public Collection<Problema> ProblemasNoVigentes() {
		Integer aux = LocalDate.now().getMonth().getValue()%3;
		return problemaRepository.findAll().stream().filter(x->!(x.getFechaPublicacion().isAfter(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue()-aux, 1)) 
													&& x.getFechaPublicacion().isBefore(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue()+(3-aux), 1)))).collect(Collectors.toList());
	}
}
