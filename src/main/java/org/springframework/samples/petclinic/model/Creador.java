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
@Table(name = "creadores")
public class Creador extends BaseEntity{
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@EmailConstraint
	@Column(unique=true)
	private String email;
	
	@Column(name = "pass")
	@NotEmpty
	private String pass;
	
	private String imagen;
	

}
