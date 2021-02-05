package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "noticias")
public class Noticia extends NamedEntity{
	
	
	@ManyToMany
	@NotNull(message= "Debe seleccionar al menos un autor")
	@NotEmpty
	Set<Tutor> autores;
	
	@ManyToOne
	@JoinColumn(name="autor_id")
	private Tutor autor;
	
	@Column(name = "fecha_publicacion")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaPublicacion;
	
	@Column(length=5600)
	@NotEmpty(message= "El campo 'Texto' no puede estar vacío")
	private String texto;
	
	private String imagen;

}
