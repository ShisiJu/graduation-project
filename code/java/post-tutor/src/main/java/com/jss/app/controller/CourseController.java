package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping("/student")
	public List<StudentCourse> findByStudent(@RequestParam Long id){
		
		return courseService.findStudentCourseByStudent_Id(id);
	}
	
	@RequestMapping("/tutor")
	public List<Course> findByTutor(@RequestParam Long id){
		
		return courseService.findTutorCourseByTutor(id);
	}
	


}
