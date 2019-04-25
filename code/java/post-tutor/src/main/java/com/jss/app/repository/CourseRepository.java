package com.jss.app.repository;


import java.util.List;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Tutor;


public interface CourseRepository extends RowBaseRepository<Course, Long>{

	List<Course> findByGroup(List<Group> group);
	
	List<Course> findByTutor_id(Long id);

	
}
