package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Envio;

public interface EnvioRepository extends Repository<Envio, String>{
	
	Collection<Envio> findAll() throws DataAccessException;
	
	Optional<Envio> findById(int id) throws DataAccessException;
	
	void save(Envio envio) throws DataAccessException;
	
	@Query(value="SELECT * FROM ENVIOS envio WHERE envio.id_alumno=:id", nativeQuery = true)
	Collection<Envio> findAllByAlumno(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM ENVIOS envio WHERE envio.id_problema=:id", nativeQuery = true)
	Collection<Envio> findAllByProblema(@Param("id") int id) throws DataAccessException;
	
}
