package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.Course;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@RequestMapping("/student")
	public List<StudentCourse> findByStudent(@RequestParam Long id) {

		return courseService.findStudentCourseByStudent_Id(id);
	}

	@RequestMapping("/studentWithPage")
	public Page<StudentCourse> findByStudentWithPage(@RequestBody JSONObject jsonObject) {

		return courseService.searchStudentCourseByStudent(jsonObject);
	}

	@RequestMapping("/tutor")
	public List<Course> findByTutor(@RequestParam Long id) {

		return courseService.findTutorCourseByTutor(id);
	}

	@RequestMapping("/search")
	public Page<Course> searchCourses(@RequestBody JSONObject joCourse) {

		return courseService.searchCourses(joCourse);
	}

	@RequestMapping("/delete")
	public void deleteCourseById(@RequestParam Long courseId) {

		courseService.deleteCourseById(courseId);
	}

	@RequestMapping("/save")
	public Course saveCourses(@RequestBody JSONObject jsonObject) {

		Course course = jsonObject.getObject("course", Course.class);
		Long tutorId = jsonObject.getLong("tutorId");

		List<Long> groupIds = null;

		if (jsonObject.getJSONArray("groupIds") != null) {
			groupIds = jsonObject.getJSONArray("groupIds").toJavaList(Long.class);
		}

		return courseService.saveCourse(course, tutorId, groupIds);
	}

	@RequestMapping("/findGroupsByCourseId")
	public List<Long> findGroupsByCourseId(Long courseId) {

		return courseService.findGroupsByCourseId(courseId);
	}

}
