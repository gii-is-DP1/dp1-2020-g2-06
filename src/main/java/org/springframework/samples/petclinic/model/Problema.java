package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.util.Utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "problema")
public class Problema extends NamedEntity {
	
	@Column(name = "id_judge")
	private Integer idJudge;
	
	@ManyToOne
	@JoinColumn(name="id_creador")
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
	@Column(name = "descripcion",length=5600)
	private String descripcion;
	
	@NotEmpty
	@Column(name = "casos_prueba")
	private String casos_prueba;
	
	@NotEmpty
	@Column(name = "salida_esperada")
	private String salida_esperada;
	
	@Column(name = "dificultad")
	private String dificultad;
	
	private String imagen;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_season")
	private Temporada season;
	
	@NotNull
	@Column(name = "season_year")
	private Integer seasonYear;
	
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problema")
	private List<Aclaracion> aclaraciones;
	
	public boolean isVigente() {
		Temporada actualSeason = Utils.getActualSeason();
		Integer actualYearSeason = Utils.getActualYearofSeason();
		return this.season.equals(actualSeason) && this.seasonYear.equals(actualYearSeason);
			
	}
	//
	//private Double puntuacionMedia = puntuacionesProblema.stream().mapToInt(x -> x.getPuntuacion()).average().getAsDouble(); 
	
}
