package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Articulo;

public interface ArticuloRepository extends Repository<Articulo, Integer>{
	
	Collection<Articulo> findAll() throws DataAccessException;
	
	Optional<Articulo> findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void save(Articulo articulo) throws DataAccessException;
	
	@Query(value="SELECT id,name,fecha_publicacion,imagen_articulo,texto FROM Articulos as a LEFT JOIN Articulos_Autores at WHERE a.id = at.articulo_id and at.autores_id = :id", nativeQuery = true)
	public Collection<Articulo> findArticulosByTutor(@Param("id") int id);

	@Query(value="SELECT id,name,fecha_publicacion,imagen_articulo,texto FROM Articulos as a LEFT JOIN Articulos_Autores at WHERE a.id = at.articulo_id and at.autores_id = :id", nativeQuery = true)
	public Slice<Articulo> findArticulosByTutorPageable(@Param("id") int id, Pageable pageable);
	
	@Query(value="SELECT * FROM Articulos articulo", nativeQuery=true)
	public Slice<Articulo> findAllArticulosPageable(Pageable pageable);

}
