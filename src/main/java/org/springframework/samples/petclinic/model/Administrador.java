package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="administradores")
public class Administrador extends BaseEntity{
	
	@Column(name="email")
	@Email
	@NotEmpty
	private String email;
	
	@Column(name="pass")
	@NotEmpty
	private String pass;
}
