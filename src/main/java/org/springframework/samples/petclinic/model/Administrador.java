package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.constraint.PassConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="administradores")
public class Administrador extends BaseEntity{
	
	@Column(name="email")
	@Email
	@NotEmpty
	private String email;
	
	@Column(name="pass")
	@NotEmpty
	@PassConstraint
	private String pass;
}
