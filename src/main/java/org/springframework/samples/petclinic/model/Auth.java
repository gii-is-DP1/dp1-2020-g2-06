package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auths")
public class Auth extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "id_alumno")
	Alumno alumno;
	
	@OneToOne
	@JoinColumn(name = "id_tutor")
	Tutor tutor;
	
	@OneToOne
	@JoinColumn(name = "id_creador")
	Creador creador;
	
	@Size(min = 3, max = 50)
	String authority;
	
	
}