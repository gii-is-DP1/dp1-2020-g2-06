package org.springframework.samples.petclinic.util;

import java.time.LocalDate;

public class Utils {

	public static String getActualSeason() {
		if(LocalDate.of(LocalDate.now().getYear(), 3, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 21))) {
			return "primavera";
		}else if(LocalDate.of(LocalDate.now().getYear(), 5, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 21))) {
			return "verano";
		}else if(LocalDate.of(LocalDate.now().getYear(), 9, 20).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 21))) {
			return "otoño";
		}else {
			return "invierno";
		}
	}
	
	public static Integer getActualYearofSeason() {
		if(getActualSeason().equals("invierno")) {
			if(LocalDate.now().getMonthValue()==12)
				return LocalDate.now().getYear();
			else
				return LocalDate.now().getYear()-1;
		}
		else {
			return LocalDate.now().getYear();
		}
	}
}
