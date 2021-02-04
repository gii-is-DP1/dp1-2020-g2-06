package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;
import org.springframework.samples.constraint.EmailConstraint;
import org.springframework.samples.constraint.PassConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auths")
public class ConfirmationToken extends BaseEntity{
	
	@NotEmpty(message= "El campo nombre no puede estar vacío")
	private String nombre;
	
	@NotEmpty(message= "El campo apellidos no puede estar vacío")
	private String apellidos;
	
	private Boolean enabled;
	
	@EmailConstraint
	@Column(unique=true)
	private String email;
	
	private String imagen;
	
	@NotEmpty
	@PassConstraint
	@JsonIgnore
	private String pass;
		
	@NotEmpty(message= "El campo token no puede estar vacío")
	private String confirmation_token;
}