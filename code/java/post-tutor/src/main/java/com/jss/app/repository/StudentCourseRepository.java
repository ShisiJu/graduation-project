package com.jss.app.repository;

import java.util.List;

import com.jss.app.model.m2m.StudentCourse;

public interface StudentCourseRepository extends RowBaseRepository<StudentCourse, Long>{
	
	List<StudentCourse> findByStudent_Id(Long id);

}
