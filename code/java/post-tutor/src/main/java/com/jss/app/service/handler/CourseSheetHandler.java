package com.jss.app.service.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.dictionary.CourseTyoe;
import com.jss.app.model.dictionary.Term;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Tutor;
import com.jss.app.service.CourseService;
import com.jss.app.service.GroupService;
import com.jss.app.service.TutorService;
import com.jss.app.util.DataBaseConvertor;

@Component
public class CourseSheetHandler implements SheetContentsHandler, HandleExcelExport {

	private Course course;

	private Tutor tutor;

	private List<Group> listGroup;

	private int dataNum = 1;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TutorService tutorService;

	@Autowired
	private GroupService groupService;

	@Override
	public void startRow(int rowNum) {
		if (rowNum > dataNum) {
			course = new Course();
			tutor = null;
			listGroup = new ArrayList<>();
		}

	}

	@Override
	@Transactional
	public void endRow(int rowNum) {
		if (rowNum > dataNum) {
			String name = course.getName();
			if ("".equals(name) || name == null) {
				return;
			}
			courseService.saveCourse(course, tutor, listGroup);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (course == null)
			return;

		String prefix = cellReference.substring(0, 1);
		formattedValue = formattedValue.trim();
		switch (prefix) {
		case "A":
			course.setName(formattedValue);
			break;
		case "B":
			course.setCode(formattedValue);
			break;
		case "C":
			course.setAcademicYear(Integer.parseInt(formattedValue));
			break;
		case "D":
			course.setTerm(Term.valueOf(formattedValue));
			break;
		case "E":
			course.setCourseType(CourseTyoe.valueOf(formattedValue));
			break;
		case "F":
			if (course.getCourseType() == CourseTyoe.选修) {
				course.setAmount(Integer.parseInt(formattedValue));
			} else {
				course.setAmount(0);
			}
			break;
		case "G":
			tutor = tutorService.findByStudno(formattedValue);
			break;
		case "H":
			String[] split = formattedValue.split(",|，");
			List<String> listName = Arrays.asList(split);
			listGroup = groupService.findByGroupNameIn(listName);
			break;
		default:
			break;
		}

	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

	}

	public void handleExportData(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		// String[] titles = "学号,姓名,性别,职称,学院名称".split(",");
		// 行
		// 获取样式 从第三行开始
		int index = dataNum + 1;

		Sort sort = new Sort(Sort.Direction.DESC, "academicYear", "term");
		List<Course> listCourse = courseService.findAllCourses(sort);

		for (Course course : listCourse) {

			Row curRow = sheet.createRow(index);
			index++;

			Cell cell = curRow.createCell(0);
			cell.setCellValue(course.getName());

			cell = curRow.createCell(1);
			cell.setCellValue(course.getCode());

			cell = curRow.createCell(2);
			cell.setCellValue(course.getAcademicYear());

			cell = curRow.createCell(3);
			cell.setCellValue(course.getTerm().toString());

			cell = curRow.createCell(4);
			cell.setCellValue(course.getCourseType().toString());

			cell = curRow.createCell(5);
			cell.setCellValue(course.getAmount());

			cell = curRow.createCell(6);
			cell.setCellValue(course.getTutor().getStudno());

			cell = curRow.createCell(7);
			List<Group> groups = courseService.findListGroupByCourseId(course.getId());
			List<String> names = new ArrayList<>();
			groups.forEach(group -> {
				names.add(group.getName());
			});

			cell.setCellValue(DataBaseConvertor.arrayJoin(names, ","));

		}

	}

	@Override
	public void handleExportData(JSONObject jsonObject, Workbook workbook) {
		// TODO Auto-generated method stub
		
	}

}
