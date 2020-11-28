package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Creador;

public interface CreadorRepository extends Repository<Creador, String> {

	Collection<Creador> findAll() throws DataAccessException;
	
	Optional<Creador> findById(int id) throws DataAccessException;
	
	void save(Creador tutor) throws DataAccessException;
	
	
}
