package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "tutores")
public class Tutor {
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellidos;
	
	@Email
	@Id
	private String email;
	
	@NotEmpty
	private String pass;

}
