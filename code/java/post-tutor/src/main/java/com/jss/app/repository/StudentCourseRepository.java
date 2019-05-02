package com.jss.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.m2m.StudentCourse;

@Transactional(readOnly = true)
public interface StudentCourseRepository extends RowBaseRepository<StudentCourse, Long> {

	List<StudentCourse> findByStudent_Id(Long id);

	@Transactional
	void deleteByCourse_id(Long id);
	
	@Transactional
	void deleteByStudent_idIn(List<Long> studentIds);

}
