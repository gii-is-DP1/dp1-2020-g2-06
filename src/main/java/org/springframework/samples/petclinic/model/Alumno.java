package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;



@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
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
	
	
	private String imagen;
	
//	@Column(name="puntos_anual")
//	private Integer puntosAnual;
//	
//	@Column(name="puntos_temporada")
//	private Integer puntosTemporada;
//	
//	@Column(name="puntos_totales")
//	private Integer puntosTotales;

	@NotEmpty
	private String pass;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
	private List<Envio> envios;
	

	
}
