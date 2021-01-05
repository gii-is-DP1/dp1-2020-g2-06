package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.model.Temporada;

public interface AlumnoRepository extends Repository<Alumno, Integer>{
	
	Collection<Alumno> findAll() throws DataAccessException;
	
	Optional<Alumno> findById(int id) throws DataAccessException;
	
	void save(Alumno alumno) throws DataAccessException;
	
	@Query("SELECT DISTINCT p FROM Problema p JOIN p.envios e WHERE e.alumno.id LIKE :id AND e.resolucion LIKE 'AC' "
			+ "AND e.season.id LIKE :season AND e.seasonYear LIKE :seasonyear AND "
			+ "p.season.id LIKE e.season.id AND p.seasonYear like e.seasonYear")
	public Collection<Problema> problemasResueltosBySeason(@Param("id") int id,@Param("season")Integer season, @Param("seasonyear")Integer seasonyear);
	
	@Query("SELECT DISTINCT p FROM Problema p JOIN p.envios e WHERE e.alumno.id LIKE :id AND e.resolucion LIKE 'AC' "
			+ "AND p.season.id LIKE e.season.id AND p.seasonYear like e.seasonYear")
	public Collection<Problema> problemasResueltos(@Param("id") int id);
	
	@Query("SELECT DISTINCT p FROM Problema p JOIN p.envios e WHERE e.alumno.id LIKE :id AND e.resolucion LIKE 'AC' "
			+ "AND p.season.id LIKE e.season.id AND p.seasonYear LIKE e.seasonYear AND YEAR(e.fecha) LIKE :year")
	public Collection<Problema> problemasResueltosDateFilter(@Param("id") int id,@Param("year")int year);
	
}
