package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Collection<Articulo> findTutorArticulos(int id) {
		return articuloRepo.findTutorArticulos(id);
	}

}
