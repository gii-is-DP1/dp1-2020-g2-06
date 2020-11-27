package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno extends BaseEntity{
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellidos;
	
	@Email
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@NotEmpty
	private String imagen;
	
	@Column(name="puntos_anual")
	private Integer puntosAnual;
	
	@Column(name="puntos_temporada")
	private Integer puntosTemporada;
	
	@Column(name="puntos_totales")
	private Integer puntosTotales;

	@NotEmpty
	private String pass;

	
}
