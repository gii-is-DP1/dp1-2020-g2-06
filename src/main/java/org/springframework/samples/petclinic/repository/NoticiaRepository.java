package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Noticia;

public interface NoticiaRepository extends CrudRepository<Noticia, Integer>{
	
	Collection<Noticia> findAll();

}
