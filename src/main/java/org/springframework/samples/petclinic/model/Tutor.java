package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "tutores")
public class Tutor{
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@Column(name = "email")
	@Email
	@Id
	private String email;
	
	@Column(name = "pass")
	@NotEmpty
	private String pass;
	
	@Column(name = "foto")
	private String foto;
	
	public boolean isNew() {
		return this.email == null;
	}

}
