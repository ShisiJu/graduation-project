package com.jss.app.service.handler;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.service.StudentCourseService;

@Component
public class StudentCourseSheetHandler implements SheetContentsHandler, HandleExcelExport {

	private StudentCourse studentCourse;

	private int dataNum = 1;

	// 学生学号
	private String studno = "";
	// 课程编码
	private String code = "";

	@Autowired
	private StudentCourseService studentCourseService;

	@Override
	public void startRow(int rowNum) {
		if (rowNum > dataNum) {
			studentCourse = new StudentCourse();
		}

	}

	@Override
	@Transactional
	public void endRow(int rowNum) {

		if (rowNum <= dataNum)
			return;

		if (studentCourse == null)
			return;

		if (studentCourse.getCourse() == null)
			return;

		studentCourseService.save(studentCourse);

	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (studentCourse == null)
			return;

		String prefix = cellReference.substring(0, 1);
		formattedValue = formattedValue.trim();

		switch (prefix) {
		case "A":
			break;
		case "B":
			code = formattedValue;
			break;
		case "C":
			studno = formattedValue;
			break;
		case "D":
			break;
		case "E":
			if (StringUtils.isEmpty(studno) || StringUtils.isEmpty(code)) {
				studentCourse = null;
				break;
			}

			StudentCourse currentObj = studentCourseService.findByStudent_StudnoAndCourse_Code(studno, code);

			if (currentObj == null) {

				studentCourseService.saveStudentCourse(studno, code);
				currentObj = studentCourseService.findByStudent_StudnoAndCourse_Code(studno, code);
			}

			if (currentObj == null) {
				break;
			}

			Integer parseInt = null;

			try {
				parseInt = Integer.parseInt(formattedValue);
			} catch (NumberFormatException e) {

			}
			currentObj.setScore(parseInt);
			studentCourse = currentObj;
			code = "";
			studno = "";
			break;
		default:
			break;
		}

	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

	}

	public void handleExportData(JSONObject jsonObject, Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		// 行
		// 获取样式 从第三行开始
		int index = dataNum + 1;
		List<StudentCourse> listStudentCourse = studentCourseService
				.searchStudentCourseByStudentWithoutPage(jsonObject);
		System.out.println("----size()-----" + listStudentCourse.size());

		for (int i = 0; i < listStudentCourse.size(); i++) {

			StudentCourse sc = listStudentCourse.get(i);
			Row curRow = sheet.createRow(index);
			index++;

			Cell cell = curRow.createCell(0);
			cell.setCellValue(sc.getCourse().getName());

			cell = curRow.createCell(1);
			cell.setCellValue(sc.getCourse().getCode());

			cell = curRow.createCell(2);
			cell.setCellValue(sc.getStudent().getStudno());

			cell = curRow.createCell(3);
			cell.setCellValue(sc.getStudent().getName());

			cell = curRow.createCell(4);
			if (sc.getScore() != null) {
				cell.setCellValue(sc.getScore());
			}
		}
		System.out.println("----size()-----" + listStudentCourse.size());
	}

	@Override
	public void handleExportData(Workbook workbook) {
		// TODO Auto-generated method stub

	}

}
