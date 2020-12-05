package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "problema")
public class Problema extends NamedEntity {
	
	@ManyToOne
	@JoinColumn(name="creador")
	private Creador creador;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problema")
	private List<Envio> envios;
	
	@Column(name = "fecha_publicacion")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaPublicacion;
	
	@Column(name = "puntuacion")
	@NotNull
	private Integer puntuacion;
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;
	
	@NotEmpty
	@Column(name = "casos_prueba")
	private String casos_prueba;
	
	@NotEmpty
	@Column(name = "salida_esperada")
	private String salida_esperada;
	
	@NotEmpty
	@Column(name = "imagen")
	private String imagen;
	
	@Column(name = "zip")
	private String zip;
	
	public String getSeason() {
		if(LocalDate.of(LocalDate.now().getYear(), 3, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 20))) {
			return "primavera";
		}else if(LocalDate.of(LocalDate.now().getYear(), 5, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 20))) {
			return "verano";
		}else if(LocalDate.of(LocalDate.now().getYear(), 9, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 20))) {
			return "otoño";
		}else {
			return "invierno";
		}
	}
	
	public boolean isVigente() {
		String season = getSeason();
		
		if(season == "primavera"){
			return LocalDate.of(LocalDate.now().getYear(), 3, 21)
					.isBefore(this.getFechaPublicacion()) && this.getFechaPublicacion().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 20));
		}else if(season == "verano") {
			return LocalDate.of(LocalDate.now().getYear(), 5, 21)
					.isBefore(this.getFechaPublicacion()) && this.getFechaPublicacion().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 20));
		}else if(season == "otoño") {
			return LocalDate.of(LocalDate.now().getYear(), 9, 21)
					.isBefore(this.getFechaPublicacion()) && this.getFechaPublicacion().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 20));
		}else {
			if(LocalDate.now().getMonthValue() == 12) {
				return LocalDate.of(LocalDate.now().getYear(), 12, 21).isBefore(this.fechaPublicacion) 
						&& this.fechaPublicacion.isBefore(LocalDate.of(LocalDate.now().getYear()+1, 3, 20));
			}else {
				return LocalDate.of(LocalDate.now().getYear()-1, 12, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 3, 20));
			}
		}
	}
}
