package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Creador;
import org.springframework.samples.petclinic.model.Publicacion;

public interface PublicacionRepository extends Repository<Publicacion,Integer>{
	
	Collection<Publicacion> findAll() throws DataAccessException;
	
	Optional<Publicacion> findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void save(Publicacion publicacion) throws DataAccessException;
	
	int count() throws DataAccessException;

	@Query(value="SELECT * FROM PUBLICACIONES publicacion", nativeQuery = true)
	public Slice<Publicacion> findAllPageable(Pageable pageable);
}
