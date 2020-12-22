package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticuloService {
	
	@Autowired
	ArticuloRepository articuloRepo;
	
	
	public Collection<Articulo> findAll(){
		return articuloRepo.findAll();
	}
	
	public Optional<Articulo> findById(int id){
		return articuloRepo.findById(id);
	}
	
	public void delete(Articulo articulo) {
		articuloRepo.deleteById(articulo.getId());
	}
	
	public void save(@Valid Articulo articulo) {
		articuloRepo.save(articulo);
	}
	
	public Collection<Articulo> findArticulosByTutor(int id) {
		return articuloRepo.findArticulosByTutor(id);
	}
	
	public Slice<Articulo> findArticulosByTutorPage(int id, Pageable pageable){
		return articuloRepo.findArticulosByTutorPageable(id, pageable);
	}

}
