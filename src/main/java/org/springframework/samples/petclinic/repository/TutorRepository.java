package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;

public interface TutorRepository extends Repository<Tutor, String> {

	Collection<Tutor> findAll() throws DataAccessException;
	
	Optional<Tutor> findById(int id) throws DataAccessException;
	
	void save(Tutor tutor) throws DataAccessException;

	@Query("SELECT DISTINCT t FROM Tutor t WHERE t.email LIKE :email")
	Optional<Tutor> findByEmail(@Param("email") String email);
	
	@Query("SELECT DISTINCT t FROM Tutor t")
	public Slice<Tutor> findTutorPage(Pageable pageable);
	
	
	

}
