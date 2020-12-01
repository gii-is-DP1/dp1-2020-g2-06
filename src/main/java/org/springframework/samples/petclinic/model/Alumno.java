package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@Data
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
	private List<Envio> envios;
	

	
}
