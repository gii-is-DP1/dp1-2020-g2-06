package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public enum dificultad {
	    ALTA,
	    MEDIA,
	    BAJA
	}
	
	public enum temporada {
	    PRIMAVERA,
	    VERANO,
	    OTOÑO,
	    INVIERNO
	}
	
	@ManyToOne
	@JoinColumn(name="creador")
	private Creador creador;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problema")
	private Set<Envio> envios;
	
	@Column(name = "fecha_publicacion")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaPublicacion;
	
	@Column(name = "puntuacion")
	@NotNull
	private Integer puntuacion;
	
	@NotEmpty
	@Column(name = "dificultad")
	private String  dificultad;
	
	@NotEmpty
	@Column(name = "temporada")
	private String temporada;
	
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
	
	protected Set<Envio> getEnviosInternal() {
		if (this.envios == null) {
			this.envios = new HashSet<>();
		}
		return this.envios;
	}

	protected void setEnviosInternal(Set<Envio> Envios) {
		this.envios = Envios;
	}

	public List<Envio> getEnvios() {
		List<Envio> sortedEnvios = new ArrayList<>(getEnviosInternal());
		PropertyComparator.sort(sortedEnvios, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedEnvios);
	}

	public void addEnvio(Envio Envio) {
		getEnviosInternal().add(Envio);
		Envio.setProblema(this);
	}
}
