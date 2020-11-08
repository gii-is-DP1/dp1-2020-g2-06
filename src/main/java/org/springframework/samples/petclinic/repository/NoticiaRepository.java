package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Noticia;

public interface NoticiaRepository extends Repository<Noticia, Integer>{
	
	Collection<Noticia> findAll() throws DataAccessException;
	
	Optional<Noticia> findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void save(Noticia noticia) throws DataAccessException;
	
	
}
