package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "comentarios")
public class Comentario extends BaseEntity{
	
	
	private String texto;
}
