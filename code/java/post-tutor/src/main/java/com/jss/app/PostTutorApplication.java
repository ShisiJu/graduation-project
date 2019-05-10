package com.jss.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.QuizAnswer;
import com.jss.app.repository.QuizAnswerRepository;
import com.jss.app.service.SatisticService;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class PostTutorApplication implements ApplicationRunner {

	@Autowired
	private QuizAnswerRepository quizAnswerRepository;
	
	@Autowired
	private SatisticService satisticService;

	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		initData();

	}

	@Transactional
	private void initData() {
		List<Map<String, Object>> satisticRecent5yearByTutorId = satisticService.satisticRecent5yearByTutorId(1L);
		System.out.println(satisticRecent5yearByTutorId);
//		List<Long> quizIds = new ArrayList<Long>();
//		quizIds.add(2L);
//		quizAnswerRepository.evalutateQuizAnswer(quizIds);
//		
		System.out.println();
		
	}

}
