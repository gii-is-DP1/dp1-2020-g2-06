package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "articulos")
public class Articulo extends NamedEntity{
	
	@ManyToOne
	@JoinColumn(name="autor_id")
	private Tutor autor;
	
	@Column(name = "fecha_publicacion")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaPublicacion;
	
	@Column(name= "imagen_articulo")
	@NotEmpty
	private String imagen;
	
	@Column(length=5600)
	@NotEmpty
	private String texto;

	
}
