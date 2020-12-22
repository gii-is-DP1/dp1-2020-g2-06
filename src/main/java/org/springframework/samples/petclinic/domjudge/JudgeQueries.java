package org.springframework.samples.petclinic.domjudge;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

///////////// Clase original donde se diseñaron las funciones. Borrar cuando se compruebe que el servicio funciona correctamente.

public class JudgeQueries {
	
	public static Run getResult(int id, int contest) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:12345/api/v4/contests/"+contest+"/runs/"+id+"?strict=false", Run.class);
	}
	
	public static Integer addSubmission(int contest,String filePath, String language, String entryPoint, String problem) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("code", new FileSystemResource(new File(filePath)));
		parameters.add("problem", problem);
		parameters.add("language", language);
		parameters.add("entry_point", entryPoint);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "multipart/form-data");
		headers.set("Accept", "text/plain");

		return Integer.parseInt(restTemplate.postForObject("http://localhost:12345/api/v4/contests/"+contest+"/submissions", 
			new HttpEntity<MultiValueMap<String, Object>>(parameters, headers), 
		    String.class));
	
	}
	
	
	public static  ProblemResponse addProblem(int contest,String filePath) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("zip[]", new FileSystemResource(new File(filePath)));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "multipart/form-data");
		headers.set("Accept", "text/plain");
		
		

		return restTemplate.postForObject("http://localhost:12345/api/v4/contests/"+contest+"/problems", 
			new HttpEntity<MultiValueMap<String, Object>>(parameters, headers), 
		    ProblemResponse.class);
	
	}
	

	
	public static String sendAndJudge(int contest, String filePath, String language, String entryPoint, String problem) throws IOException, InterruptedException {

		Integer runNumber = addSubmission(contest,filePath,language,entryPoint,problem);
		Integer seconds = 15;
		String veredict = "Error inesperado, inténtalo de nuevo más tarde.";
		
		while(seconds>=0) {
		try {
			veredict = getResult(runNumber,contest).getJudgementTypeId();
			break;
		}catch (Exception e) {
			Thread.sleep(1000);
		}
		
		seconds--;
		}
		return veredict;
	}
	
	

	
}

