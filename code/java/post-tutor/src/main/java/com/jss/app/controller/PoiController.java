package com.jss.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.service.PoiService;

@Controller
@RequestMapping(value = "/poi")
public class PoiController {

	@Autowired
	private PoiService poiService;

	// student-course 导入导出
	@RequestMapping(value = "/export-student-course")
	public void exportStudentCourse(@RequestBody JSONObject jsonObject, HttpServletResponse res) {

		try {

			poiService.exportStudentCourse(res, jsonObject, "student-course.xlsx");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/export-student-course-model")
	public void exportStudentCourseModel(HttpServletResponse res) {

		try {

			poiService.exportStudentCourseModel(res, "student-course-model.xlsx");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/import-student-course")
	@ResponseBody
	public void importStudentCourse(@RequestParam(name = "file") MultipartFile file) {
		try {
			poiService.importStudentCourse(file);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// tutor 导入导出
	@RequestMapping(value = "/export-tutor")
	public void exportTutor(HttpServletResponse res) {

		try {

			poiService.exportTutor(res, "tutor.xlsx");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/export-tutor-model")
	public void exportTutorModel(HttpServletResponse res) {

		try {
			poiService.exportTutorModel(res, "tutor-model.xlsx");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/import-tutor")
	@ResponseBody
	public void importTutor(@RequestParam(name = "file") MultipartFile file) {
		try {
			poiService.importTutor(file);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// 课程导入导出
	@RequestMapping(value = "/export-course")
	public void exportCourse(HttpServletResponse res) {

		try {

			poiService.exportCourse(res, "course.xlsx");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/export-course-model")
	public void exportCourseModel(HttpServletResponse res) {

		try {
			poiService.exportCourseModel(res, "course-model.xlsx");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/import-course")
	@ResponseBody
	public void importCourse(@RequestParam(name = "file") MultipartFile file) {
		try {
			poiService.importCourse(file);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// 学生导入导出
	@RequestMapping(value = "/export-student")
	public void exportStudent(HttpServletResponse res) {
		try {
			poiService.exportStudent(res, "student.xlsx");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/export-student-model")
	public void exportStudentModel(HttpServletResponse res) {

		try {
			poiService.exportStudentModel(res, "student-model.xlsx");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/import-student")
	@ResponseBody
	public void importStudent(@RequestParam(name = "file") MultipartFile file) {
		try {
			poiService.importStudent(file);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
