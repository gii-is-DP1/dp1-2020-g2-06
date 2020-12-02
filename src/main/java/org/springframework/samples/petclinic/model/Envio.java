package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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
	
	
	public String getCodigoString() throws IOException {
		return Files.readString(Paths.get(codigoPath), StandardCharsets.UTF_8);
	}

}
