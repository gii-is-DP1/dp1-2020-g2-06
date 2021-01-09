package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Tutor;

public interface TutorRepository extends Repository<Tutor, String> {

	Collection<Tutor> findAll() throws DataAccessException;
	
	Optional<Tutor> findById(int id) throws DataAccessException;
	
	@Query(value="SELECT ID FROM TUTORES WHERE EMAIL LIKE :email", nativeQuery = true)
	Integer findIdByEmail(String email) throws DataAccessException;
	
	void save(Tutor tutor) throws DataAccessException;

}
