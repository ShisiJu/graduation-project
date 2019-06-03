package com.jss.app.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;

@Transactional(readOnly = true)
public interface CourseRepository extends RowBaseRepository<Course, Long> {

	List<Course> findByGroup(List<Group> group);

	List<Course> findByTutor_id(Long id);

	Course findByCode(String code);

	@Query(value = "SELECT * FROM t_course tc LEFT OUTER JOIN t_tutor tt ON tc.tutor_id = tt.id WHERE tc.course_type = 1 AND tc.amount > tc.current_num AND tc.id NOT IN( SELECT tsc.course_id FROM t_student_course tsc WHERE tsc.student_id = ?1)", nativeQuery = true)
	List<Course> findOptioinalCoursesByStudentId(Long studentId, Pageable pageable);

	@Query(value = " SELECT COUNT(1)  FROM t_course tc LEFT OUTER JOIN t_tutor tt ON tc.tutor_id = tt.id WHERE tc.course_type = 1  AND tc.amount > tc.current_num AND  tc.academic_year = YEAR(NOW()) AND tc.term = IF ( MONTH (NOW()) < 8 && MONTH (NOW()) > 2, 1, 0) AND  tc.id NOT IN( SELECT tsc.course_id FROM t_student_course tsc WHERE tsc.student_id = ?1) ", nativeQuery = true)
	BigInteger countOptioinalCoursesByStudentId(Long studentId);

	@Query(value = "SELECT t.group_id FROM t_course_group t WHERE t.course_id = ?1", nativeQuery = true)
	List<BigInteger> findGroupIdsByCourseId(Long id);

	@Query(value = "DELETE FROM t_course_group  WHERE course_id = ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteCourseGroupByCourseId(Long id);
}
