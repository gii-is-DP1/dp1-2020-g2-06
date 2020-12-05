package org.springframework.samples.petclinic.util;

import java.time.LocalDate;

public class Utils {

	public static String getActualSeason() {
		if(LocalDate.of(LocalDate.now().getYear(), 3, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 20))) {
			return "primavera";
		}else if(LocalDate.of(LocalDate.now().getYear(), 5, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 20))) {
			return "verano";
		}else if(LocalDate.of(LocalDate.now().getYear(), 9, 21).isBefore(LocalDate.now()) 
				&& LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 20))) {
			return "otoño";
		}else {
			return "invierno";
		}
	}
}
