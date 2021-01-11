package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Aclaracion;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.PreguntaTutor;

public interface PreguntaTutorRepository extends Repository<PreguntaTutor, String> {

	Collection<PreguntaTutor> findAll() throws DataAccessException;
	
	Optional<PreguntaTutor> findById(int id) throws DataAccessException;
	
	void save(PreguntaTutor tutor) throws DataAccessException;
	
//	@Query(value="SELECT * FROM pregunta WHERE pregunta.id_alumno=:id", nativeQuery = true)
//	Collection<PreguntaTutor> findAllByAlumno(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM pregunta WHERE pregunta.id_problema=:id", nativeQuery = true)
	Collection<PreguntaTutor> findAllByProblema(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT DISTINCT p FROM PreguntaTutor p WHERE p.tutor=null")
	Collection<PreguntaTutor> findByProblemaNotAnswered();

	@Query(value="SELECT DISTINCT p FROM PreguntaTutor p WHERE p.alumno.id=:id")
	Collection<PreguntaTutor> findByAlumnoId(@Param("id") int id);

	@Query(value="SELECT DISTINCT p FROM PreguntaTutor p WHERE p.alumno.id=:id AND p.tutor=null")
	Collection<PreguntaTutor> findByAlumnoIdNoRespondidas(int id);
	
	@Query(value="SELECT DISTINCT p FROM PreguntaTutor p WHERE p.alumno.id=:id AND p.tutor!=null")
	Collection<PreguntaTutor> findByAlumnoIdRespondidas(int id);
}
