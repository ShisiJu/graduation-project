package com.jss.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.m2m.StudentCourse;

@Transactional(readOnly = true)
public interface StudentCourseRepository extends RowBaseRepository<StudentCourse, Long> {

	List<StudentCourse> findByStudent_Id(Long id);

	StudentCourse findByStudent_StudnoAndCourse_Code(String studno, String code);

	List<StudentCourse> findByCourse_Tutor_Studno(String tutorStudno);

	@Transactional
	@Query(value = "INSERT INTO t_student_course (student_id, course_id) VALUES ( ?1, ?2)", nativeQuery = true)
	@Modifying
	void insertByStduentIdAndCourseId(Long studentId, Long courseId);

	@Transactional
	void deleteByCourse_id(Long id);

	@Transactional
	void deleteByStudent_idIn(List<Long> studentIds);

}
