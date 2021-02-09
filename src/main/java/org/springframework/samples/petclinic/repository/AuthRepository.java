package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Auth;
import org.springframework.samples.petclinic.model.Problema;



public interface AuthRepository extends  CrudRepository<Auth, String>{
	
	Optional<Auth> findById(int id) throws DataAccessException;
}
