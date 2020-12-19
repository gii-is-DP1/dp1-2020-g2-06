package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.PuntuacionProblema;

public interface PuntuacionProblemaRepository extends Repository<PuntuacionProblema, String>{
	
	Collection<PuntuacionProblema> findAll() throws DataAccessException;
	
	Optional<PuntuacionProblema> findById(int id) throws DataAccessException;
	
	void save(PuntuacionProblema puntProb) throws DataAccessException;
	
	@Query(value="SELECT * FROM puntuacionProblema WHERE puntuacionProblema.id_alumno=:id", nativeQuery = true)
	Collection<PuntuacionProblema> findAllByAlumno(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM puntuacionProblema WHERE puntuacionProblema.id_problema=:id", nativeQuery = true)
	Collection<PuntuacionProblema> findAllByProblema(@Param("id") int id) throws DataAccessException;
	
	@Query(value="SELECT avg(puntuacionProblema.puntuacion) FROM puntuacionProblema WHERE puntuacionProblema.id_problema=:id", nativeQuery = true)
	Collection<PuntuacionProblema> averageAllByProblema(@Param("id") int id) throws DataAccessException;
}
