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

	@Query(value="SELECT idJudge,creador,envios,fechaPublicacion,puntuacion,descripcion,casos_prueba,salida_esperada,imagen,zip,season,seasonYear,competicion,puntuacionesProblema,aclaraciones FROM Problemas as a LEFT JOIN Problemas_Creadores at WHERE a.id = at.creador and at.creador = :id", nativeQuery = true)
	public Collection<Problema> findAllByCreador(@Param("id") int id);

}
