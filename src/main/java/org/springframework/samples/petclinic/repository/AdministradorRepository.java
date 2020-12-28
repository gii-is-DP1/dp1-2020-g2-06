package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Tutor;

public interface AdministradorRepository extends Repository<Tutor, String> {

	Collection<Administrador> findAll() throws DataAccessException;
	
	Optional<Administrador> findById(int id) throws DataAccessException;
	
	void save(Administrador administrador) throws DataAccessException;

}
