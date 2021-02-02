package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Noticia;

public interface CreadorRepository extends Repository<Creador, String> {

	Collection<Creador> findAll() throws DataAccessException;
	
	Optional<Creador> findById(int id) throws DataAccessException;
	
	void save(Creador tutor) throws DataAccessException;

	@Query("SELECT DISTINCT c FROM Creador c WHERE c.email LIKE :email")
	Optional<Creador> findByEmail(@Param("email") String email);
	
	@Query(value="SELECT * FROM CREADORES creador", nativeQuery = true)
	public Slice<Creador> findAllPageable(Pageable pageable);
	
}
