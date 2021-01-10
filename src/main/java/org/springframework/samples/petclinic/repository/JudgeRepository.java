package org.springframework.samples.petclinic.repository;

import java.io.IOException;

import org.springframework.samples.petclinic.domjudge.Judgement;
import org.springframework.samples.petclinic.domjudge.ProblemResponse;
import org.springframework.web.multipart.MultipartFile;

public interface JudgeRepository {
	
	Judgement getResult(int id, int contest);
	
	Integer addSubmission(int contest,String filePath, String language, String entryPoint, int problem);
	
	ProblemResponse addProblem(int contest,String filePath);
	
	String judge(int contest, int runNumber)throws InterruptedException ;

}
