package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;
import org.springframework.samples.constraint.EmailConstraint;
import org.springframework.samples.constraint.PassConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "alumnos")
public class Alumno extends BaseEntity{
	
	@NotEmpty(message= "El campo 'Nombre' no puede estar vacío")
	private String nombre;
	
	@NotEmpty(message= "El campo 'Apellidos' no puede estar vacío")
	private String apellidos;
	
	private Boolean enabled;
    
	@EmailConstraint
	@Column(unique=true)
	private String email;
	
	private String imagen;
	
	
	@PassConstraint
	@JsonIgnore
	private String pass;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
	@JsonIgnore
	private List<Envio> envios;
	
	@NonNull
	private Boolean compartir;
	
	@NonNull
	private String confirmation_token;
	
}
