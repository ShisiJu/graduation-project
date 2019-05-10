package com.jss.app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.QuizAnswer;

@Transactional
public interface QuizAnswerRepository extends RowBaseRepository<QuizAnswer, Long> {

	@Modifying
	@Query(value = "insert QuizAnswer qa set qa.quiz = ?1 where u.lastname = ?2", nativeQuery = true)
	int setFixedFirstnameFor(String firstname, String lastname);

	List<QuizAnswer> findByQuizIn(List<Quiz> listQuiz);

	@Query(value = "SELECT count(t.answer) amount,SUM(IF(t.answer = 'A', 1, 0)) AS A,SUM(IF(t.answer = 'B', 1, 0)) AS B,SUM(IF(t.answer = 'C', 1, 0)) AS C,SUM(IF(t.answer = 'D', 1, 0)) AS D "
			+ " FROM t_quiz_answer t " + " WHERE t.type = 0 and quiz_id in ?1 ", nativeQuery = true)
	Map<String, Object> evalutateQuizAnswer(List<Long> quizIds);

	List<QuizAnswer> findByQuiz_StudentCourse_Course_Tutor_idAndTypeAndQuiz_StudentCourse_Course_academicYearBetween(
			Long tutorId, Integer type, Integer lowYear, Integer highYear);

	@Query(value = "SELECT id FROM t_quiz_answer tqa,(SELECT GROUP_CONCAT(tq.id) AS tqIds FROM t_quiz tq,"
			+ "(SELECT GROUP_CONCAT(tsc.id) AS scIds FROM t_student_course tsc,"
			+ "(SELECT GROUP_CONCAT(tc.id) AS courseIds FROM t_course tc "
			+ "WHERE tc.tutor_id = ?1 AND tc.academic_year = ?2 AND tc.term = ?3 ) tc "
			+ "WHERE FIND_IN_SET(tsc.course_id, tc.courseIds) ) tsc "
			+ "WHERE FIND_IN_SET( tq.student_course_id, tsc.scIds)) tq "
			+ "WHERE tqa.type = 0 AND FIND_IN_SET(tqa.id, tq.tqIds)", nativeQuery = true)
	List<Long> evaluateSpecific(Long tutorId, Integer academicYear, Integer term);


}
