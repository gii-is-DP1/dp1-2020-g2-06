package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)

@Entity
@Table(name="envios")
public class Envio extends BaseEntity{

	@NotEmpty
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
	
	private String season;  /// redundante pero necesario para query
	
	@Column(name = "season_year")
	private Integer seasonYear;   /// redundante pero necesario para query
	
	public List<String> getCodigoString() throws IOException {
		return Files.readAllLines(Paths.get(codigoPath));
	}
	
	
	public String getSeason() {

		if(LocalDate.of(LocalDate.now().getYear(), 3, 21).isBefore(this.fecha.toLocalDate()) 
				&& this.fecha.toLocalDate().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 20))) {
			return "primavera";
		}else if(LocalDate.of(LocalDate.now().getYear(), 5, 21).isBefore(this.fecha.toLocalDate()) 
				&& this.fecha.toLocalDate().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 20))) {
			return "verano";
		}else if(LocalDate.of(LocalDate.now().getYear(), 9, 21).isBefore(this.fecha.toLocalDate()) 
				&& this.fecha.toLocalDate().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 20))) {
			return "otoño";
		}else {
			return "invierno";
		}
	}
	
	public Integer getYearofSeason() {
		if(getSeason().equals("invierno")) {
			if(fecha.getMonthValue()==12)
				return fecha.getYear();
			else
				return fecha.getYear()-1;
		}
		else {
			return fecha.getYear();
		}
		
	}
			
	

}
