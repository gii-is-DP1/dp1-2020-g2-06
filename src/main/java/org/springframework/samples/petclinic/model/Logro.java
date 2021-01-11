package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "logros")
public class Logro extends BaseEntity{

	
	@Column(name="nombre_logro")
	@NotEmpty
	private String nombre;
	
	private String imagen; 
}
