package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="envios")
public class Envio extends BaseEntity{
	
	@Column(name = "id_judge")
	@NotNull
	private Integer idJudge;

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime fecha;

	@Column(name="codigo_path")
	@NotEmpty
	private String codigoPath;
	
	private String resolucion;
	
	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	
	@ManyToOne
	@JoinColumn(name="id_problema")
	private Problema problema;
	
	@ManyToOne
	@JoinColumn(name="id_season")
	private Temporada season;  /// redundante pero necesario para query
	
	@Column(name = "season_year")
	private Integer seasonYear;   /// redundante pero necesario para query

	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "envio")
	private List<Comentario> listaComentarios;
	
	public List<String> getCodigoString() throws IOException {
		return Files.readAllLines(Paths.get(codigoPath));
	}
	
	public String getFecha() {
		String month = String.valueOf(fecha.getMonthValue());
		if(month.length()==1) 
			month = 0 + month;
			
		return fecha.getDayOfMonth()+"/"+month+"/"+fecha.getYear()+" "+fecha.getHour()+":"+fecha.getMinute();
	}

}
