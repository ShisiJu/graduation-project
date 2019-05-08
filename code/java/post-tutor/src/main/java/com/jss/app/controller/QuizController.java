package com.jss.app.controller;

import java.util.ArrayList;
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
import com.jss.app.service.CourseService;
import com.jss.app.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private CourseService courseService;

	@RequestMapping("/custom")
	public List<QuizAnswer> findByCustom(@RequestBody JSONObject jsonObject) {

		List<Course> listCourse = courseService.searchCoursesWithoutPage(jsonObject);

		List<Long> listId = new ArrayList<>();
		
		listCourse.forEach(c -> {
			listId.add(c.getId());
		});

		return quizService.findQuizAnswerByCourseIn(listId);

	}

	@RequestMapping("/quizMap")
	public void insertQuizMap(@RequestBody JSONObject jsonQuiz) {

		Long studentCourseId = jsonQuiz.getLong("studentCourseId");
		Quiz quiz = jsonQuiz.getObject("quiz", Quiz.class);
		JSONArray jsonArray = jsonQuiz.getJSONArray("quizAnswers");
		List<QuizAnswer> quizAnswers = jsonArray.toJavaList(QuizAnswer.class);
		quizService.insertQuizWithQuizAnswers(studentCourseId, quiz, quizAnswers);
	}

	@RequestMapping("/detail")
	public List<QuizAnswer> findByTutor(@RequestBody Course course) {

		return quizService.findQuizAnswerByCourse(course);
	}

}
