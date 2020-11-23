package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "problema")
public class Problema extends NamedEntity {

	public enum dificultad {
	    ALTA,
	    MEDIA,
	    BAJA
	}
	
	public enum temporada {
	    PRIMAVERA,
	    VERANO,
	    OTOÑO,
	    INVIERNO
	}
	
	
	@Column(name = "puntuacion")
	@NotNull
	private Integer puntuacion;
	
	@NotEmpty
	@Column(name = "dificultad")
	private String  dificultad;
	
	@NotEmpty
	@Column(name = "temporada")
	private String temporada;
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;
	
	@NotEmpty
	@Column(name = "casos_prueba")
	private String casos_prueba;
	
	@NotEmpty
	@Column(name = "salida_esperada")
	private String salida_esperada;
	
	@NotEmpty
	@Column(name = "imagen")
	private String imagen;
	
	@Column(name = "zip")
	private String zip;
}
