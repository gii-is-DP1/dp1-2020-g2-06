package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="temporada")
@Entity
public class Temporada extends BaseEntity implements Comparable<Temporada>{
	


	@NotEmpty
	private String nombre;

	@Override
	public int compareTo(Temporada o) {
		return this.id.compareTo(o.id);
	}

}
