package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.NormaWeb;

public interface NormaWebRepository extends Repository<NormaWeb,Integer>{

	Collection<NormaWeb> findAll() throws DataAccessException;
	
	Optional<NormaWeb> findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void save(NormaWeb normaWeb) throws DataAccessException;
	
	int count() throws DataAccessException;
}
