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
@Table(name = "normas_web")
public class NormaWeb extends NamedEntity {
	
	@ManyToOne
	@JoinColumn(name="autor_email")
	private Tutor autor;
	
	@NotEmpty
	@Column(length = 5600)
	private String descripcion;
}
