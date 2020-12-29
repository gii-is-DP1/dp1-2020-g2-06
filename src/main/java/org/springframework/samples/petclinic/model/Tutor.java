package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.constraint.EmailConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tutores")
public class Tutor extends BaseEntity{
	
	@Column(name = "nombre")
	@NotEmpty(message= "El campo nombre no puede estar vacío")
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty(message= "El campo apellidos no puede estar vacío")
	private String apellidos;
	
	@EmailConstraint
	@Column(unique=true)
	private String email;
	
	@Column(name = "pass")
	@NotEmpty
	private String pass;
	
	@Column(name = "imagen")
	private String imagen;

	

}
