package com.jss.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.app.service.QuizService;

@RestController
@RequestMapping("/statistic")
public class SatisticController {

	@Autowired
	private QuizService quizService;

	
	@RequestMapping("/tutor")
	public Map<String, Object> evaluateCourseByTutorId(Long tutorId) {

		return null;
	}

}
