package org.springframework.samples.petclinic.model;

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
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@NotNull
	private LocalDateTime fecha_inicio;
	
	@Column(name = "fecha_fin")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@NotNull
	private LocalDateTime fecha_fin;
	
	@Column(name= "imagen")
	private String imagen;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "competicion")
	private List<Problema> problemas;
	
//	@ManyToMany(mappedBy = "competicion")
//	private List<Alumno> alumnos;
}

