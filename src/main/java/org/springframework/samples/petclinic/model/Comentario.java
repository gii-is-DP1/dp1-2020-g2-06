package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)

@Entity
@Table(name="comentarios")
public class Comentario extends BaseEntity{	
	
	@NotEmpty
	@ManyToOne
	@JoinColumn(name="id_envio")
	private Envio envio;
	
	@NotEmpty
	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;

	@Column(length=500)
	@NotEmpty
	private String texto;
	

}
