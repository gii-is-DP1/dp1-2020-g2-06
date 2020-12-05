package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Transactional
	public Collection<Alumno> findAll(){
		return alumnoRepository.findAll();
	}
	
	public Optional<Alumno> findById(int id){
		return alumnoRepository.findById(id);
	}
	
	public void save(Alumno alumno) {
		alumnoRepository.save(alumno);
	}
	
	public Collection<Problema> problemasResueltos(int id){
		return alumnoRepository.problemasResueltos(id);
	}
	
	public Collection<Problema> problemasResueltosThisYear(int id){
		
		return alumnoRepository.problemasResueltosDateFilter(id, LocalDate.now().getYear());
	}
	
	public Collection<Problema> problemasResueltosThisSeason(int id){
		return alumnoRepository.problemasResueltosBySeason(id, Utils.getActualSeason(), Utils.getActualYearofSeason());
	}
	


}
