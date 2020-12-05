package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;

public interface AlumnoRepository extends Repository<Alumno, Integer>{
	
	Collection<Alumno> findAll() throws DataAccessException;
	
	Optional<Alumno> findById(int id) throws DataAccessException;
	
	void save(Alumno alumno) throws DataAccessException;
	
	@Query("SELECT DISTINCT p FROM Problema p JOIN p.envios e WHERE e.alumno.id LIKE :id AND e.resolucion LIKE 'AC'")
	public Collection<Problema> problemasResueltos(@Param("id") int id);
	

}
