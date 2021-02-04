package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.constraint.EmailConstraint;
import org.springframework.samples.constraint.PassConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "creadores")
public class Creador extends BaseEntity{
	
	@Column(name = "nombre")
	@NotEmpty(message= "El campo 'Nombre' no puede estar vacío")
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty(message= "El campo 'Apellidos' no puede estar vacío")
	private String apellidos;
	
	private Boolean enabled;
	
	@EmailConstraint
	@Column(unique=true)
	private String email;
	
	@Column(name = "pass")
	@PassConstraint
	@JsonIgnore
	private String pass;
	
	private String imagen;
	

}
