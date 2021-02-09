package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="aclaraciones")
public class Aclaracion extends BaseEntity{	
	
	/*Cada aclaración está asociada a 1 tutor, y cada uno tiene varias aclaraciones, por lo que le asociamos el parametro Tutor con una relación ManyToOne*/
	@ManyToOne
	@JoinColumn(name="id_tutor")
	private Tutor tutor;
	
	/*Cada problema tiene asociado muchas aclaraciones*/
	@ManyToOne
	@JoinColumn(name="id_problema")
	private Problema problema;
	
	/*La aclaración es un texto que escribe un tutor, por lo que debe tener un String texto*/
	@NotEmpty
	private String texto;
	

}
