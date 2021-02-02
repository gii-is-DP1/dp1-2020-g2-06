package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.repository.ProblemaRepository;
import org.springframework.samples.petclinic.util.Utils;
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
		return problemaRepository.findAllVigente(Utils.getActualYearofSeason(),Utils.getActualSeason().getId());
	}
	
	public Collection<Problema> ProblemasNoVigentes(Collection<Problema> cp) {
		return problemaRepository.findAllNoVigente(Utils.getActualYearofSeason(),Utils.getActualSeason().getId());
	}
	
	public Slice<Problema> ProblemasNoVigentePage(Pageable pageable){
		return problemaRepository.findAllNoVigentePage(pageable, Utils.getActualYearofSeason(),Utils.getActualSeason().getId());
	}
	
	public Collection<Problema> findAllByCreador(int id) {
		return problemaRepository.findAllByCreador(id);
	}
}
