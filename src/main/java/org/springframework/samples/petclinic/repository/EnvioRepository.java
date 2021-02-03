package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
	
	@Query(value="SELECT DISTINCT ID_PROBLEMA FROM ENVIOS envio WHERE envio.id_alumno=:id AND envio.resolucion = 'AC'", nativeQuery = true)
	Collection<Integer> findAllByAlumnoAc(@Param("id") int id) throws DataAccessException;
	
	@Query(value="SELECT DISTINCT ID_PROBLEMA FROM ENVIOS envio WHERE envio.id_alumno=:id AND envio.resolucion = 'WA'", nativeQuery = true)
	Collection<Integer> findAllByAlumnoWa(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM ENVIOS envio WHERE envio.id_problema=:id ", nativeQuery = true)
	Collection<Envio> findAllByProblema(@Param("id") int id) throws DataAccessException;
	
	@Query(value="SELECT e FROM Envio e WHERE e.problema.id LIKE :id")
	Slice<Envio> findAllByProblemaPage(Pageable pageable,@Param("id") int id) throws DataAccessException;
	
	@Query(value="SELECT DISTINCT ID_ALUMNO FROM ENVIOS envio  WHERE envio.id_problema=:id AND envio.resolucion = 'AC'", nativeQuery = true)
	Collection<Integer> findAllByProblemaAC(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT e FROM Envio e WHERE e.alumno.id LIKE :id")
	Slice<Envio> findAllByAlumnoPage(Pageable pageable, @Param("id") int id) throws DataAccessException;

	
}
