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
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.entity.User;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.repository.UserRepository;
import com.jss.app.service.TutorService;

@Component
public class TutorSheetHandler implements SheetContentsHandler, HandleExcelExport {

	private Tutor tutor;

	private int dataNum = 1;

	@Autowired
	private InstituteRepository instituteRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private TutorService tutorService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void startRow(int rowNum) {
		if (rowNum > dataNum) {
			tutor = new Tutor();
		}

	}

	@Override
	@Transactional
	public void endRow(int rowNum) {
		if (rowNum > dataNum) {
			String studno = tutor.getStudno();

			if ("".equals(studno) || studno == null) {
				return;
			}
			tutorRepository.save(tutor);
			User user = new User();
			user.setPwd(studno);
			user.setStudno(studno);
			user.setType(1);
			user.setUsername(studno);
			userRepository.save(user);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (tutor == null)
			return;


		String prefix = cellReference.substring(0, 1);

		switch (prefix) {
		case "A":
			tutor.setStudno(formattedValue);
			break;
		case "B":
			tutor.setName(formattedValue);
			break;
		case "C":
			tutor.setSex(Sex.valueOf(formattedValue));
			break;
		case "D":
			tutor.setTitle(formattedValue);
			break;
		case "E":
			Institute findByName = instituteRepository.findByName(formattedValue);
			tutor.setInstitute(findByName);
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
		
		List<Tutor> listTutor = tutorService.searchTutorsWithoutPage(jsonObject);

		for (Tutor tutor : listTutor) {

			Row curRow = sheet.createRow(index);
			index++;

			Cell cell = curRow.createCell(0);
			cell.setCellValue(tutor.getStudno());

			cell = curRow.createCell(1);
			cell.setCellValue(tutor.getName());

			cell = curRow.createCell(2);
			cell.setCellValue(tutor.getSex().toString());

			cell = curRow.createCell(3);
			cell.setCellValue(tutor.getTitle());

			cell = curRow.createCell(4);
			cell.setCellValue(tutor.getInstitute().getName());

		}

	}

}
