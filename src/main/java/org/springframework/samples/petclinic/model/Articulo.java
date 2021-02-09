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
import org.springframework.samples.petclinic.util.Utils;

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
	private LocalDate fechaPublicacion;
	
	@Column(name= "imagen_articulo")
	private String imagen;
	
	@Column(length=5600)
	@NotEmpty(message= "El campo 'Texto' no puede estar vacío")
	private String texto;
	
	public String getFechaPublicacionFormat() {
		return fechaPublicacion.getDayOfMonth() + " de " + Utils.getMonthName(fechaPublicacion.getMonthValue())  + " de " + fechaPublicacion.getYear();
	}


	
}
