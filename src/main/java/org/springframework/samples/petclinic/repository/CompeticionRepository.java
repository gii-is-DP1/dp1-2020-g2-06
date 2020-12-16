package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Competicion;

public interface CompeticionRepository extends Repository<Competicion, String> {

	Collection<Competicion> findAll() throws DataAccessException;
	
	Optional<Competicion> findById(int id) throws DataAccessException;
	
	void save(Competicion competicion) throws DataAccessException;
	
}
