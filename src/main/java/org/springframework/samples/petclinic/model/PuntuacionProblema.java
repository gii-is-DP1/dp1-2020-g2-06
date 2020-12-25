package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="puntuacionProblema")
public class PuntuacionProblema extends BaseEntity{	
	
	@NotEmpty
	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	@NotEmpty
	@ManyToOne
	@JoinColumn(name="id_problema")
	private Problema problema;
	
	private Integer puntuacion;
	

}
