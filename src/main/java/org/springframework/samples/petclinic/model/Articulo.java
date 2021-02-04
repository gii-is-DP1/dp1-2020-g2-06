package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "articulos")
public class Articulo extends NamedEntity{
	
	@ManyToMany
	@NotNull
	@NotEmpty(message= "Debe seleccionar al menos un autor")
	Set<Tutor> autores;
	
	@Column(name = "fecha_publicacion")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(message= "Debe indicar una fecha de publicación")
	private LocalDate fechaPublicacion;
	
	@Column(name= "imagen_articulo")
	private String imagen;
	
	@Column(length=5600)
	@NotEmpty(message= "Debe introducir un texto")
	private String texto;

	
}
