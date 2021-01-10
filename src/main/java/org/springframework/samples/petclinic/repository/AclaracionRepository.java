package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Aclaracion;

public interface AclaracionRepository extends Repository<Aclaracion, String>{
	
	Collection<Aclaracion> findAll() throws DataAccessException;
	
	Optional<Aclaracion> findById(int id) throws DataAccessException;
	
	void save(Aclaracion aclaracion) throws DataAccessException;
	
	@Query(value="SELECT * FROM aclaraciones WHERE aclaraciones.id_tutor=:id", nativeQuery = true)
	Collection<Aclaracion> findAllByTutor(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM aclaraciones WHERE aclaraciones.id_problema=:id", nativeQuery = true)
	Collection<Aclaracion> findAllByProblema(@Param("id") int id) throws DataAccessException;
	
}
