package com.jss.app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.QuizAnswer;

@Transactional
public interface QuizAnswerRepository extends RowBaseRepository<QuizAnswer, Long> {

	final String STASTICS = ", IFNULL( SUM(IF(tqa.answer = 'A', 1, 0)), 0) A, IFNULL( SUM(IF(tqa.answer = 'B', 1, 0)), 0 ) B, IFNULL( SUM(IF(tqa.answer = 'C', 1, 0)), 0 ) C, IFNULL( SUM(IF(tqa.answer = 'D', 1, 0)), 0 ) D, COUNT(tqa.answer) amount"
			+ ", SUM(IF(tqa.answer = 'A', 1, 0)) / COUNT(tqa.answer) * 100 AS APercent, SUM(IF(tqa.answer = 'B', 1, 0)) / COUNT(tqa.answer) * 100 AS BPercent, SUM(IF(tqa.answer = 'C', 1, 0)) / COUNT(tqa.answer) * 100 AS CPercent, SUM(IF(tqa.answer = 'D', 1, 0)) / COUNT(tqa.answer) * 100 AS DPercent "
			+ " FROM t_tutor tt LEFT OUTER JOIN t_course tc ON tt.id = tc.tutor_id LEFT OUTER JOIN t_student_course tsc ON tsc.course_id = tc.id LEFT OUTER JOIN t_quiz tq ON tq.student_course_id = tsc.id LEFT OUTER JOIN t_quiz_answer tqa ON tqa.quiz_id = tq.id  LEFT OUTER JOIN t_institute ti ON tt.institute_id = ti.id   WHERE tqa.type = 0 ";

	List<QuizAnswer> findByQuizIn(List<Quiz> listQuiz);

	List<QuizAnswer> findByQuiz_StudentCourse_Course_Tutor_idAndTypeAndQuiz_StudentCourse_Course_academicYearBetween(
			Long tutorId, Integer type, Integer lowYear, Integer highYear);

	@Query(value = " SELECT tc.academic_year, tc.term " + STASTICS
			+ "AND tt.id = ?1 AND tc.academic_year = ?2 AND tc.term = ?3 ", nativeQuery = true)
	Map<String, Object> evalutateRecentYearAndTerm(Long tutorId, Integer academicYear, Integer term);

	@Query(value = "SELECT tt.id,tt.studno, tt.`name`, IF (tt.sex = 0, '男', '女') sex, tt.title " + STASTICS
			+ " GROUP BY tt.id ORDER BY APercent DESC,BPercent DESC LIMIT 0,10", nativeQuery = true)
	List<Map<String, Object>> evalutateByTutor();

	@Query(value = "SELECT tt.id,tt.studno, tt.`name`, IF (tt.sex = 0, '男', '女') sex, tt.title " + STASTICS
			+ " GROUP BY tt.id ORDER BY DPercent DESC,CPercent DESC LIMIT 0,10", nativeQuery = true)
	List<Map<String, Object>> evalutateByTutorDesc();

	@Query(value = "SELECT tt.id,tt.studno, tt.`name`, IF (tt.sex = 0, '男', '女') sex, tt.title " + STASTICS
			+ " AND tqa.question = ?1 GROUP BY tt.id ORDER BY APercent DESC,BPercent DESC LIMIT 0,10", nativeQuery = true)
	List<Map<String, Object>> evalutateByTutorAndQuestion(Integer question);

	@Query(value = "SELECT tt.id,tt.studno, tt.`name`, IF (tt.sex = 0, '男', '女') sex, tt.title " + STASTICS
			+ " AND tqa.question = ?1 GROUP BY tt.id ORDER BY DPercent DESC,CPercent DESC LIMIT 0,10", nativeQuery = true)
	List<Map<String, Object>> evalutateByTutorAndQuestionDesc(Integer question);

	@Query(value = "SELECT tt.institute_id, ti.`name`, ti.`code` " + STASTICS
			+ " GROUP BY tt.institute_id ORDER BY APercent DESC,BPercent DESC ", nativeQuery = true)
	List<Map<String, Object>> evalutateByInstitute();

	@Query(value = "SELECT tt.institute_id, ti.`name`, ti.`code` " + STASTICS
			+ " GROUP BY tt.institute_id ORDER BY  DPercent DESC,CPercent DESC ", nativeQuery = true)
	List<Map<String, Object>> evalutateByInstituteDesc();

}
