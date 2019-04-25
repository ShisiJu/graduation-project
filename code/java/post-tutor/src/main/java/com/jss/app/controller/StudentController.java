package com.jss.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;
import com.jss.app.service.StudentService;


@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@RequestMapping("/info")
	public Student getStudentInfo(@RequestParam Long id) {
		return studentService.findStudentById(id);
	}
	
	@RequestMapping("/group")
	public void getStudentGroup(@RequestParam Group group) {
		studentService.findStudentByGroup(group);
	}
	
}
