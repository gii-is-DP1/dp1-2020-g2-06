package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.PuntuacionProblema;
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
		problemaRepository.save(problema);
	}	
	
	public Collection<Problema> ProblemasVigentes() {
		return problemaRepository.findAll().stream().filter(x->x.isVigente()&&x.getCompeticion()==null).collect(Collectors.toList());
	}
	
	public Collection<Problema> ProblemasNoVigentes(Collection<Problema> cp) {
		return problemaRepository.findAll().stream().filter(x->!x.isVigente()&&x.getFechaPublicacion().isBefore(LocalDate.now())&&x.getCompeticion()==null).collect(Collectors.toList());
	}
	
	public Double valoracionMediaAlumnno(Problema pr) {
		if(pr.getPuntuacionesProblema().isEmpty()) {
			return -1.0;
		}
		return pr.getPuntuacionesProblema().stream().mapToDouble(x->x.getPuntuacion()).average().getAsDouble();
	}
}
