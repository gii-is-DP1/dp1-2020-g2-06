package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Logro;

public interface LogroRepository extends Repository<Logro, Integer>{

	Collection<Logro> findAll() throws DataAccessException;
	
	Optional<Logro> findById(int id) throws DataAccessException;
	
	
}
