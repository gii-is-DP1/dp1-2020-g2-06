package org.springframework.samples.petclinic.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.constraint.FechaFinConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "competicion")
public class Competicion extends BaseEntity{
	
	@Column(name = "nombre_competicion")
	@NotEmpty
	private String nombre;
	
	@Column(name = "descripcion")
	@NotEmpty
	private String descripcion;
	
	@Column(name = "fecha_inicio")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha_inicio;
	
	@Column(name = "hora_inicio")
	@DateTimeFormat(pattern = "HH:mm")
	@NotNull
	private Time hora_inicio;
	
	@Column(name = "fecha_fin") 
	@FechaFinConstraint
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fecha_fin;
	
	@Column(name = "hora_fin")
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private Time hora_fin;
	
	@Column(name= "imagen")
	private String imagen;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "competicion")
	private List<Problema> problemas;
	
//	@ManyToMany(mappedBy = "competicion")
//	private List<Alumno> alumnos;
}

