package org.springframework.samples.petclinic.service;

import java.io.File;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.samples.petclinic.domjudge.Judgement;
import org.springframework.samples.petclinic.domjudge.ProblemResponse;
import org.springframework.samples.petclinic.domjudge.Run;
import org.springframework.samples.petclinic.repository.JudgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class JudgeService implements JudgeRepository {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String host = "http://localhost:12345";
	
	public Judgement getResult(int id, int contest) {
		//restTemplate = new RestTemplate();
		return restTemplate.getForObject(host+"/api/v4/contests/"+contest+"/judgements/"+id+"?strict=false", Judgement.class);
	}
	
	public Integer addSubmission(int contest,String filePath, String language, String entryPoint, int problem) {
		//restTemplate = new RestTemplate();

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		
//		parameters.add("entry_point", entryPoint.chars());
		parameters.add("problem", String.valueOf(problem));
		parameters.add("language", language);
		parameters.add("code[]", new FileSystemResource(new File(filePath)));
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "multipart/form-data");
//		headers.set("Accept", "text/plain");
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(parameters, headers);


		return Integer.parseInt(restTemplate.postForObject(host+"/api/v4/contests/"+contest+"/submissions", 
			entity, 
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
	

	
	public String judge(int contest, int runNumber) throws InterruptedException {

		Integer seconds = 25;
		String veredict = "Error inesperado, inténtelo de nuevo más tarde.";
		
		while(seconds>=0 && (!veredict.equals("AC")||!veredict.equals("CE")||!veredict.equals("WA")||!veredict.equals("TLE"))) {
		try {
			veredict = getResult(runNumber,contest).getJudgementTypeId();
			if(veredict==null)
				veredict="Error inesperado, inténtelo de nuevo más tarde.";
		}catch (Exception e) {
			
		}
		Thread.sleep(1000);
		seconds--;
		}
		return veredict;
	}
	
}