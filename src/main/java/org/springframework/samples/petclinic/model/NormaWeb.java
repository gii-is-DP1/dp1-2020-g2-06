package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class NormaWeb extends NamedEntity {

	@NotEmpty
	@NotNull
	private String nombre;

	@NotEmpty
	@NotNull
	private String descripcion;
}
