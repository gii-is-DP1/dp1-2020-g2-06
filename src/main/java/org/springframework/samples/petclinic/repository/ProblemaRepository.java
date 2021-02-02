package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Envio;
import org.springframework.samples.petclinic.model.Problema;

public interface ProblemaRepository extends Repository<Problema,Integer>{

	Collection<Problema> findAll() throws DataAccessException;
	
	Optional<Problema> findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void save(Problema problema) throws DataAccessException;
	
	int count() throws DataAccessException;

	@Query(value="SELECT p FROM Problema p WHERE p.creador.id LIKE :id")
	public Collection<Problema> findAllByCreador(@Param("id") int id);
	
	@Query(value="SELECT p FROM Problema p WHERE p.seasonYear LIKE :year AND p.season.id LIKE :season_id")
	public Collection<Problema> findAllVigente(@Param("year") int year, @Param("season_id")int seasonId);	
	

}
