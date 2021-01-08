package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.Temporada;

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
				return LocalDate.now().getYear()-1;
		}
		else {
			return LocalDate.now().getYear();
		}
	}
	
	public static String diferenciador(String extension) {
		return LocalDate.now().getYear() + "" + LocalDate.now().getMonthValue() + "" + LocalDate.now().getDayOfMonth() + "" +
				+ LocalDateTime.now().getHour() + "" + LocalDateTime.now().getMinute() + "" + LocalDateTime.now().getSecond() +
				+ LocalDateTime.now().getNano() + "." + extension;
	}
}
