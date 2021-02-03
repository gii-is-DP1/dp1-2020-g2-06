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
	
	@ManyToOne
	@JoinColumn(name="id_tutor")
	private Tutor tutor;
	
	@ManyToOne
	@JoinColumn(name="id_problema")
	private Problema problema;
	
	@NotEmpty
	private String texto;
	

}
