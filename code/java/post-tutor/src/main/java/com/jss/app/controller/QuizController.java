package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.QuizAnswer;
import com.jss.app.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@RequestMapping("/quizMap")
	public void insertQuizMap(@RequestBody JSONObject jsonQuiz) {
		
		Long studentCourseId = jsonQuiz.getLong("studentCourseId");
		Quiz quiz = jsonQuiz.getObject("quiz", Quiz.class);
		JSONArray jsonArray = jsonQuiz.getJSONArray("quizAnswers");
		List<QuizAnswer> quizAnswers = jsonArray.toJavaList(QuizAnswer.class);
		quizService.insertQuizWithQuizAnswers(studentCourseId, quiz, quizAnswers);
	}

	@RequestMapping("/detail")
	public List<QuizAnswer> findBTutor(@RequestBody Course course) {
		
		return quizService.findQuizAnswerByCourse(course);
	}

}
