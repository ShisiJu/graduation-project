package com.jss.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.service.StudentCourseService;

@RestController
@RequestMapping("/student-course")
public class StudentCourseController {

	@Autowired
	private StudentCourseService studentCourseService;

	// 通用 StudentCourse 分页查询
	@RequestMapping("/search")
	public Page<StudentCourse> findByStudentWithPage(@RequestBody JSONObject jsonObject) {

		return studentCourseService.searchStudentCourseByStudent(jsonObject);
	}

	@RequestMapping("/delete")
	public void deleteCourseById(@RequestParam Long id) {

		studentCourseService.deleteStudentCourse(id);
	}

	@RequestMapping("/save")
	public void save(Long studentId, Long courseId) {

		studentCourseService.saveStudentCourse(studentId, courseId);
	}

	@RequestMapping("/update-score")
	public void saveEntity(Integer score, Long id) {
		studentCourseService.updateScore(id, score);
	}

}