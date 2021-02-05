package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.util.Pair;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Articulo;
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		alumno.setPass(encoder.encode(alumno.getPass()));
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
	
	public void sendMail(Alumno alumno, JavaMailSender javaMailSender) {
		String remitente = "information.codeus@gmail.com";
		String destinatario = alumno.getEmail();
		
		String clave = "CodeUs2001DP1";
		
		String token = alumno.getNombre().substring(0, 3) + alumno.getApellidos().substring(0, 3) + alumno.getEmail().substring(4, 7) + "CDU1";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", clave);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject("Verificación codeUs");
		msg.setFrom(remitente);
		msg.setTo(destinatario);
		msg.setText("Buenas " + alumno.getNombre() + ",\n" + "para poder acceder a codeUs, haz click en el siguiente enlace para verificar tu correo.\nhttp:localhost/alumnos/confirmation/" + token + " \n Gracias por unirte! Bienvenido!");
		
		javaMailSender.send(msg);
		
	}
}
