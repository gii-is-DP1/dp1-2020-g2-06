package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "problema_auxiliar")
public class ProblemaAuxiliar extends NamedEntity {
	
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
	
	private String season;
	
	@Column(name = "season_year")
	private Integer seasonYear;
	
	public boolean isVigente() {
		String actualSeason = Utils.getActualSeason();
		Integer actualYearSeason = Utils.getActualYearofSeason();
		return this.season.equals(actualSeason) && this.seasonYear.equals(actualYearSeason);
			
	}
	
	public Problema problemaConZip(MultipartFile zip) {
		Problema res = new Problema();
		res.setName(this.getName());
		res.setDescripcion(descripcion);
		res.setCasos_prueba(casos_prueba);
		res.setSalida_esperada(salida_esperada);
		res.setFechaPublicacion(fechaPublicacion);
		res.setImagen(imagen);
		res.setPuntuacion(puntuacion);
		res.setSeason(season);
		res.setSeasonYear(seasonYear);
		res.setZip("uploads/" + zip.getOriginalFilename());
		return res;
	}
	
}
