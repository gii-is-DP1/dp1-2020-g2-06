package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.domjudge.ProblemResponse;
import org.springframework.samples.petclinic.domjudge.Run;

public interface JudgeRepository {
	
	Run getResult(int id, int contest);
	
	Integer addSubmission(int contest,String filePath, String language, String entryPoint, int problem);
	
	ProblemResponse addProblem(int contest,String filePath);
	
	String sendAndJudge(int contest, String filePath, String language, String entryPoint, int problem)throws InterruptedException ;

}
