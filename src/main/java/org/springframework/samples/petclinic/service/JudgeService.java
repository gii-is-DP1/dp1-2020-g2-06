package org.springframework.samples.petclinic.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.samples.petclinic.domjudge.ProblemResponse;
import org.springframework.samples.petclinic.domjudge.Run;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class JudgeService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String host = "http://localhost:12345";
	
	public Run getResult(int id, int contest) {
		//restTemplate = new RestTemplate();
		return restTemplate.getForObject(host+"/api/v4/contests/"+contest+"/runs/"+id+"?strict=false", Run.class);
	}
	
	public Integer addSubmission(int contest,String filePath, String language, String entryPoint, int problem) {
		//restTemplate = new RestTemplate();

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("code", new FileSystemResource(new File(filePath)));
		parameters.add("problem", String.valueOf(problem));
		parameters.add("language", language);
		parameters.add("entry_point", entryPoint);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "multipart/form-data");
		headers.set("Accept", "text/plain");

		return Integer.parseInt(restTemplate.postForObject(host+"/api/v4/contests/"+contest+"/submissions", 
			new HttpEntity<MultiValueMap<String, Object>>(parameters, headers), 
		    String.class));
	
	}
	
	
	public ProblemResponse addProblem(int contest,String filePath) {
		//restTemplate = new RestTemplate();

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("zip[]", new FileSystemResource(new File(filePath)));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "multipart/form-data");
		headers.set("Accept", "text/plain");
		
		

		return restTemplate.postForObject(host+"/api/v4/contests/"+contest+"/problems", 
			new HttpEntity<MultiValueMap<String, Object>>(parameters, headers), 
		    ProblemResponse.class);
	
	}
	

	
	public String sendAndJudge(int contest, String filePath, String language, String entryPoint, int problem) throws IOException, InterruptedException {

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
