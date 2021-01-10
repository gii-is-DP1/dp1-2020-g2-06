package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository extends Repository<Comentario, String>{
	
	Collection<Comentario> findAll() throws DataAccessException;
	
	Optional<Comentario> findById(int id) throws DataAccessException;
	
	void save(Comentario comentario) throws DataAccessException;
	
	@Query(value="SELECT * FROM comentarios WHERE comentarios.id_alumno=:id", nativeQuery = true)
	Collection<Comentario> findAllByAlumno(@Param("id") int id) throws DataAccessException;

	@Query(value="SELECT * FROM comentarios WHERE comentarios.id_envio=:id", nativeQuery = true)
	Collection<Comentario> findAllByEnvio(@Param("id") int id) throws DataAccessException;
	
}
