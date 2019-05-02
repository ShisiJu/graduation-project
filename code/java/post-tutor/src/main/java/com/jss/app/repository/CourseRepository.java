package com.jss.app.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;

@Transactional(readOnly=true)
public interface CourseRepository extends RowBaseRepository<Course, Long> {

	List<Course> findByGroup(List<Group> group);

	List<Course> findByTutor_id(Long id);

	@Query(value = "SELECT t.group_id FROM t_course_group t WHERE t.course_id = ?1", nativeQuery = true)
	List<BigInteger> findGroupIdsByCourseId(Long id);
	
	@Query(value = "DELETE FROM t_course_group  WHERE course_id = ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteCourseGroupByCourseId(Long id);
}
