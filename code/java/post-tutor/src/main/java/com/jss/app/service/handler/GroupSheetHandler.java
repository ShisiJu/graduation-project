package com.jss.app.service.handler;

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

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;

@Component
public class GroupSheetHandler implements SheetContentsHandler, HandleExcelExport {

	private Group group;

	private int dataNum = 1;

	@Autowired
	private InstituteRepository instituteRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public void startRow(int rowNum) {
		if (rowNum > dataNum) {
			group = new Group();
		}

	}

	@Override
	@Transactional
	public void endRow(int rowNum) {
		if (rowNum > dataNum) {
			String code = group.getCode();

			if ("".equals(code) || code == null) {
				return;
			}
			groupRepository.save(group);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (group == null)
			return;

		String prefix = cellReference.substring(0, 1);

		switch (prefix) {
		case "A":
			group.setName(formattedValue);
			break;
		case "B":
			group.setCode(formattedValue);
			break;
		case "C":
			group.setAcademicYear(Integer.parseInt(formattedValue));
			break;
		case "D":
			Institute findByName = instituteRepository.findByName(formattedValue);
			group.setInstitute(findByName);
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

		Sort sort = new Sort(Sort.Direction.ASC, "institute");
		List<Group> listGroup = groupRepository.findAll(sort);

		for (Group group : listGroup) {

			Row curRow = sheet.createRow(index);
			index++;

			Cell cell = curRow.createCell(0);
			cell.setCellValue(group.getName());

			cell = curRow.createCell(1);
			cell.setCellValue(group.getCode());

			cell = curRow.createCell(2);
			cell.setCellValue(group.getAcademicYear());

			cell = curRow.createCell(3);
			cell.setCellValue(group.getInstitute().getName());

		}

	}

}
