package com.jss.app.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.QuizAnswer;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.QuizAnswerRepository;
import com.jss.app.repository.QuizRepository;
import com.jss.app.repository.StudentCourseRepository;

@Transactional(readOnly = true)
@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuizAnswerRepository quizAnswerRepository;
	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insertQuizWithQuizAnswers(Long studentCourseId, Quiz quiz, List<QuizAnswer> quizAnswers) {

		Optional<StudentCourse> optStudentCourse = studentCourseRepository.findById(studentCourseId);
		StudentCourse studentCourse = optStudentCourse.get();

		quiz.setStudentCourse(studentCourse);
		Quiz safedQuiz = quizRepository.save(quiz);

		// 需要给每一个answer加上具体的quiz
		quizAnswers.forEach(answer -> {
			answer.setQuiz(safedQuiz);
		});

		batchUpateOrSaveQuizAnswers(quizAnswers);

		// 标记为已评价
		studentCourse.setStatus(1);
		studentCourseRepository.save(studentCourse);

	}

	public List<QuizAnswer> findQuizAnswerByCourse(Course course) {

		List<Quiz> listQuiz = quizRepository.findByStudentCourse_Course(course);
		return quizAnswerRepository.findByQuizIn(listQuiz);
	}

	public List<QuizAnswer> findQuizAnswerByCourseIn(List<Long> listId) {

		List<Quiz> listQuiz = quizRepository.findByStudentCourse_Course_idIn(listId);
		return quizAnswerRepository.findByQuizIn(listQuiz);
	}

	// 批量插入效率高
	@Transactional
	public void batchUpateOrSaveQuizAnswers(List<QuizAnswer> quizAnswers) {

		for (int i = 0; i < quizAnswers.size(); i++) {
			// 更新或插入
			em.merge(quizAnswers.get(i));
			if (i % 30 == 0) {
				em.flush();
				em.clear();
			}
		}
	}

}
