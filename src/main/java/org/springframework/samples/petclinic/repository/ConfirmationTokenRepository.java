package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Integer>{
	
	Collection<ConfirmationToken> findAll() throws DataAccessException;
	
	Optional<ConfirmationToken> findById(int id) throws DataAccessException;
	
	
	void save(ConfirmationToken confirmationToken) throws DataAccessException;
	
	@Query("SELECT DISTINCT ct FROM ConfirmationToken ct WHERE ct.email LIKE :email")
	Optional<ConfirmationToken> findByEmail(@Param("email") String email);
	
	@Query("SELECT DISTINCT ct FROM ConfirmationToken ct WHERE ct.confirmation_token LIKE :confirmation_token")
	Optional<ConfirmationToken> findByToken(@Param("confirmation_token") String confirmation_token);
	
	
}
