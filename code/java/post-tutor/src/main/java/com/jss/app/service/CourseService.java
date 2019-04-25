package com.jss.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.CourseRepository;
import com.jss.app.repository.StudentCourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	public List<StudentCourse> findStudentCourseByStudent_Id(Long id) {

		return studentCourseRepository.findByStudent_Id(id);
	}

	public List<Course> findTutorCourseByTutor(Long id) {

		return courseRepository.findByTutor_id(id);

	}

}
