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

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.User;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.UserRepository;
import com.jss.app.service.StudentService;

@Component
public class StudentSheetHandler implements SheetContentsHandler, HandleExcelExport {

	private Student student;

	private int dataNum = 1;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void startRow(int rowNum) {
		if (rowNum > dataNum) {
			student = new Student();
		}

	}

	@Override
	@Transactional
	public void endRow(int rowNum) {
		if (rowNum > dataNum) {
			String studno = student.getStudno();

			if ("".equals(studno) || studno == null) {
				return;
			}
			studentRepository.save(student);
			User user = new User();
			user.setPwd(studno);
			user.setStudno(studno);
			user.setType(0);
			user.setUsername(studno);
			userRepository.save(user);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (student == null)
			return;

		String prefix = cellReference.substring(0, 1);

		switch (prefix) {
		case "A":
			student.setStudno(formattedValue);
			break;
		case "B":
			student.setName(formattedValue);
			break;
		case "C":
			student.setSex(Sex.valueOf(formattedValue));
			break;
		case "D":
			Group findByName = groupRepository.findByName(formattedValue.trim());
			student.setGroup(findByName);
			break;
		default:
			break;
		}

	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

	}

	public void handleExportData(Workbook workbook) {
		handleExportData(null, workbook);
	}

	@Override
	public void handleExportData(JSONObject jsonObject, Workbook workbook) {

		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}

		Sheet sheet = workbook.getSheetAt(0);
		int index = dataNum + 1;

		List<Student> listStudent = studentService.searchStudentsWithoutPage(jsonObject);

		for (Student student : listStudent) {

			Row curRow = sheet.createRow(index);
			index++;

			Cell cell = curRow.createCell(0);
			cell.setCellValue(student.getStudno());

			cell = curRow.createCell(1);
			cell.setCellValue(student.getName());

			cell = curRow.createCell(2);
			cell.setCellValue(student.getSex().toString());

			cell = curRow.createCell(3);
			cell.setCellValue(student.getGroup().getName());

		}

	}

}
