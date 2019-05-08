package com.jss.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Quiz;

@Transactional
public interface QuizRepository extends RowBaseRepository<Quiz, Long> {

	@Modifying
	@Query(value = "INSERT INTO t_quiz(student_course_id,anonymous,template)  VALUES (?1,?2,?3)", nativeQuery = true)
	int insertQuiz(Long student_course_id, Integer anonymous, Integer template);

	List<Quiz> findByStudentCourse_Course(Course course);

	List<Quiz> findByStudentCourse_Course_idIn(List<Long> listId);

}
