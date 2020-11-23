package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "normas_web")
public class NormaWeb extends NamedEntity {
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;
}
