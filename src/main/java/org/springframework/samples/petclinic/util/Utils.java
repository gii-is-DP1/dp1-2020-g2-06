package org.springframework.samples.petclinic.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.samples.petclinic.model.Temporada;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.CreadorService;
import org.springframework.samples.petclinic.service.FileService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

	public static Temporada getActualSeason() {
		if(LocalDate.of(LocalDate.now().getYear(), 3, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 21))) {
			Temporada temp = new Temporada();
			temp.setId(0);
			temp.setNombre("primavera");
			return temp;
		}else if(LocalDate.of(LocalDate.now().getYear(), 5, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 21))) {
			Temporada temp = new Temporada();
			temp.setId(1);
			temp.setNombre("verano");
			return temp;
		}else if(LocalDate.of(LocalDate.now().getYear(), 9, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 21))) {
			Temporada temp = new Temporada();
			temp.setId(2);
			temp.setNombre("otoño");
			return temp;
		}else {
			Temporada temp = new Temporada();
			temp.setId(3);
			temp.setNombre("invierno");
			return temp;
		}
	}
	
	public static Integer getActualYearofSeason() {
		Temporada temp = new Temporada();
		temp.setId(3);
		temp.setNombre("invierno");
		if(getActualSeason().equals(temp)) {
			if(LocalDate.now().getMonthValue()==12)
				return LocalDate.now().getYear();
			else
				return LocalDate.now().getYear();
		}
		else {
			return LocalDate.now().getYear()-1;
		}
	}
	
	public static String diferenciador(String extension) {
		return LocalDate.now().getYear() + "" + LocalDate.now().getMonthValue() + "" + LocalDate.now().getDayOfMonth() + "" +
				+ LocalDateTime.now().getHour() + "" + LocalDateTime.now().getMinute() + "" + LocalDateTime.now().getSecond() +
				+ LocalDateTime.now().getNano() + "." + extension;
	}
	
	
	public static String authLoggedIn() {
		SecurityContext sc = SecurityContextHolder.getContext();
		if(sc==null)
			return null;
		else {
			return  sc.getAuthentication().getAuthorities().toString()
					.substring(1,sc.getAuthentication().getAuthorities()
							.toString().length()-1);
				
		}
		
	}
	
	public static boolean CorreoExistente(String email,AlumnoService alumnoService,TutorService tutorService,CreadorService creadorService,AdministradorService administradorService) {
		boolean alumno = alumnoService.findByEmail(email).isPresent();
		if(alumno) {
			return true;
		}
		boolean tutor = tutorService.findByEmail(email).isPresent();
		if(tutor) {
			return true;
		}
		boolean creador = creadorService.findByEmail(email).isPresent();
		if(creador) {
			return true;
		}
		boolean administrador = administradorService.findByEmail(email).isPresent();
		if(administrador) {
			return true;
		}
		return false;
	}

}
