package com.jss.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;
import com.jss.app.service.StudentService;

@RequestMapping("/student")
@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/page")
	public Page<Student> getStudentsByStudnoAndGroupId(@RequestBody JSONObject jsonObject) {
		// index 是以0开始 service做了处理
		return studentService.searchStudents(jsonObject);
	}

	@RequestMapping("/info")
	public Student getStudentInfo(@RequestParam Long id) {
		return studentService.findStudentById(id);
	}

	@RequestMapping("/group")
	public void getStudentGroup(@RequestParam Group group) {
		studentService.findStudentByGroup(group);
	}

	@RequestMapping("/count")
	public Long countStudents() {

		return studentService.countStudents();
	}

	@RequestMapping("/update")
	public Student updateStudent(@RequestBody JSONObject studentWithGroup) {

		Student student = studentWithGroup.getJSONObject("student").toJavaObject(Student.class);
		Long groupId = studentWithGroup.getLong("groupId");
		return studentService.updateStudent(student, groupId);
	}

	@RequestMapping("/insert")
	public Student insertStudent(@RequestBody JSONObject studentWithGroup) {

		Student student = studentWithGroup.getJSONObject("student").toJavaObject(Student.class);
		Long groupId = studentWithGroup.getLong("groupId");
		return studentService.insertStudent(student, groupId);
	}

	@RequestMapping("/delete")
	public void delete(@RequestParam Long id) {

		studentService.deleteStudentById(id);
	}

}
