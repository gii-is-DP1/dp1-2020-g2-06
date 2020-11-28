package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Alumno;

public interface AlumnoRepository extends Repository<Alumno, Integer>{
	
	Collection<Alumno> findAll() throws DataAccessException;
	
	Optional<Alumno> findById(int id) throws DataAccessException;
	
	void save(Alumno alumno) throws DataAccessException;

}
