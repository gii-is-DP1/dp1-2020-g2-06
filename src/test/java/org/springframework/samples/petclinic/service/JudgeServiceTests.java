package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.domjudge.Judgement;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class JudgeServiceTests {
	
	@Autowired
	JudgeService judgeService;
	
	@Autowired
	FileService fileService;

	@Test
	public void shouldGetResult() throws InterruptedException {
		Integer send = judgeService.addSubmission(2,"codes/prueba.c","c","",1);
		Thread.sleep(12000);
		Judgement j = judgeService.getResult(send, 2);
		assertThat(j.getJudgementTypeId()).isEqualTo("TLE");
	}
	
	@Test
	public void shouldaddSubmission() {
		Integer first = judgeService.addSubmission(2,"codes/prueba.c","c","",1);
		Integer second = judgeService.addSubmission(2,"codes/prueba.c","c","",1);

		assertThat(second).isEqualTo(first+1);
	}
	
	@Test
	public void shouldaddProblem() throws IOException, InterruptedException {
		String dif1 = Utils.diferenciador("zip");
		Thread.sleep(100);
		String dif2 = Utils.diferenciador("zip");
		Files.copy(Paths.get("uploads/202012281844516611200.zip"), Paths.get("uploads/"+dif1));
		
		Files.copy(Paths.get("uploads/202012281844516611200.zip"), Paths.get("uploads/"+dif2));

		Integer first = judgeService.addProblem(2, "uploads/"+dif1).getProblemIds().get(0);
		Integer second = judgeService.addProblem(2, "uploads/"+dif2).getProblemIds().get(0);
		
		fileService.delete(Paths.get("uploads/"+dif1));
		fileService.delete(Paths.get("uploads/"+dif2));

		assertThat(second).isEqualTo(first+1);
	}
	
	@Test
	public void shouldJudge() throws InterruptedException {
		Integer send = judgeService.addSubmission(2,"codes/prueba.c","c","",1);
		assertThat(judgeService.judge(2,send)).isEqualTo("TLE");
	}
	
	
}
