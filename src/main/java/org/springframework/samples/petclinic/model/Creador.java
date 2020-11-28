package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Data
@Entity
@Table(name = "creadores")
public class Creador extends BaseEntity{
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@Email
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@Column(name = "pass")
	@NotEmpty
	private String pass;
	
	@Column(name = "foto")
	private String foto;
	

}
