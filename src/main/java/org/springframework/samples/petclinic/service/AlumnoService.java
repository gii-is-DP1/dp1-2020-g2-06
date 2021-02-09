package org.springframework.samples.petclinic.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.util.Pair;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Problema;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Transactional
	public Collection<Alumno> findAll(){
		return alumnoRepository.findAll();
	}
	
	public Optional<Alumno> findById(int id){
		return alumnoRepository.findById(id);
	}
	
	public void save(Alumno alumno) {
		if(alumno.getEnabled()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			alumno.setPass(encoder.encode(alumno.getPass()));
		}
		alumnoRepository.save(alumno);
	}
	
	public Collection<Problema> problemasResueltos(int id){
		return alumnoRepository.problemasResueltos(id);
	}
	
	public Collection<Problema> problemasResueltosThisYear(int id){
		
		return alumnoRepository.problemasResueltosDateFilter(id, LocalDate.now().getYear());
	}
	
	public Collection<Problema> problemasResueltosThisSeason(int id){
		return alumnoRepository.problemasResueltosBySeason(id, Utils.getActualSeason().getId(), Utils.getActualYearofSeason());
	}
	
	public List<Pair<Alumno, Integer>> rankingTotal(){
		Collection<Alumno> all = findAll();
		List<Pair<Alumno,Integer>> res = new ArrayList<Pair<Alumno,Integer>>();
		
		for(Alumno a: all) {
			res.add(Pair.of(a,problemasResueltos(a.getId()).stream().mapToInt(x->x.getPuntuacion()).sum()));
		}
		Collections.sort(res, Comparator.comparing(Pair::getSecond));
		Collections.reverse(res);

		int fin = 10;
		if(res.size()<10)
			fin = res.size();
		return res.subList(0, fin);
	}
	
	public List<Pair<Alumno, Integer>> rankingTemporada(){
		Collection<Alumno> all = findAll();
		List<Pair<Alumno,Integer>> res = new ArrayList<Pair<Alumno,Integer>>();
		
		for(Alumno a: all) {
			res.add(Pair.of(a,problemasResueltosThisSeason(a.getId()).stream().mapToInt(x->x.getPuntuacion()).sum()));
		}
		Collections.sort(res, Comparator.comparing(Pair::getSecond));
		Collections.reverse(res);
		
		int fin = 10;
		if(res.size()<10)
			fin = res.size();
		return res.subList(0, fin);
	}
	
	public List<Pair<Alumno, Integer>> rankingAnual(){
		Collection<Alumno> all = findAll();
		List<Pair<Alumno,Integer>> res = new ArrayList<Pair<Alumno,Integer>>();
		
		for(Alumno a: all) {
			res.add(Pair.of(a,problemasResueltosThisYear(a.getId()).stream().mapToInt(x->x.getPuntuacion()).sum()));
		}
		Collections.sort(res, Comparator.comparing(Pair::getSecond));
		Collections.reverse(res);
		
		int fin = 10;
		if(res.size()<10)
			fin = res.size();
		return res.subList(0, fin);
	}

	public Optional<Alumno> findByEmail(String email) {
		return alumnoRepository.findByEmail(email);
	}

	public Collection<Alumno> sortedByPunctuation() {
		return alumnoRepository.findAll();
	}
	public Slice<Alumno> findAllPage(Pageable pageable){
		return alumnoRepository.findAllPageable(pageable);
	}

	public Optional<Alumno> findByToken(String confirmation_token) {
		return alumnoRepository.findByToken(confirmation_token);
	}
	
	public void sendMail(Alumno alumno, JavaMailSender javaMailSender) throws AddressException, MessagingException, UnsupportedEncodingException {
		List<Alumno> alSl = this.findAll().stream().collect(Collectors.toList());
		System.out.println("TOKTOK");
		for (int i = 0; i < alSl.size(); i++) {
			Alumno alum = alSl.get(i);
			String toktok = alum.getPass().substring(6, 8)
					+alum.getNombre().substring(alum.getNombre().length()/3, 2*alum.getNombre().length()/3) 
					+ alum.getPass().substring(2, 4)
					+ alum.getApellidos().substring(alum.getApellidos().length()/3, 2*alum.getApellidos().length()/3)
					+ alum.getPass().substring(0, 2)
					+ alum.getEmail().split("@")[0]
					+ alum.getPass().substring(4, 6);
			System.out.println(Base64.getEncoder().encodeToString(toktok.getBytes()));
		}
		
		
		
		
		String destinatario = alumno.getEmail();
		//String clave = "fvop bsna sxrq nbno";
		String token = "";
		
		token = alumno.getPass().substring(6, 8)
				+alumno.getNombre().substring(alumno.getNombre().length()/3, 2*alumno.getNombre().length()/3) 
				+ alumno.getPass().substring(2, 4)
				+ alumno.getApellidos().substring(alumno.getApellidos().length()/3, 2*alumno.getApellidos().length()/3)
				+ alumno.getPass().substring(0, 2)
				+ alumno.getEmail().split("@")[0]
				+ alumno.getPass().substring(4, 6);
		
		token = Base64.getEncoder().encodeToString(token.getBytes());

		alumno.setConfirmation_token(token);
		
    	String user = "information.codeus@gmail.com";
    	String pass = "CodeUsDP1@";
 
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
 
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(user, pass);
		    }
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));
		message.setRecipients(
		  Message.RecipientType.TO, InternetAddress.parse(destinatario));
		message.setSubject("Correo de verificación codeUs");
 
		String msg = "Buenas " + alumno.getNombre() + ",<br>" + "para poder acceder a codeUs, haz click en el siguiente enlace para verificar tu correo: <br><a href='http://localhost/alumnos/confirmation/" + token +"'> http://localhost/alumnos/confirmation/" + token +" </a> <br>Gracias por unirte! Bienvenido!";

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");
 
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
 
		message.setContent(multipart);
 
		Transport.send(message);
	}
}
