package com.jss.app.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.jss.app.service.handler.CourseSheetHandler;
import com.jss.app.service.handler.GroupSheetHandler;
import com.jss.app.service.handler.HandleExcelExport;
import com.jss.app.service.handler.StudentSheetHandler;
import com.jss.app.service.handler.TutorSheetHandler;
import com.jss.app.util.ResponseUtil;

@Service
public class PoiService {

	@Autowired
	private TutorSheetHandler tutorSheetHandler;

	@Autowired
	private StudentSheetHandler studentSheetHandler;

	@Autowired
	private GroupSheetHandler groupSheetHandler;

	@Autowired
	private CourseSheetHandler courseSheetHandler;

	// 对 导师 导入 导出模版 导出数据
	public void importCourse(MultipartFile file) throws Exception {

		importData(file, courseSheetHandler);
	}

	public void exportCourseModel(HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {

		Resource resource = new ClassPathResource("excel-model/course-model.xlsx");
		File file = resource.getFile();
		// 输出文件
		ResponseUtil.download(response, file, fileName);
	}

	public void exportCourse(HttpServletResponse response, String fileName) throws IOException, InvalidFormatException {

		exportData(courseSheetHandler, "excel-model/course-model.xlsx", response, fileName);
	}

	// 对 导师 导入 导出模版 导出数据
	public void importTutor(MultipartFile file) throws Exception {

		importData(file, tutorSheetHandler);
	}

	public void exportTutorModel(HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {

		Resource resource = new ClassPathResource("excel-model/tutor-model.xlsx");
		File file = resource.getFile();
		// 输出文件
		ResponseUtil.download(response, file, fileName);
	}

	public void exportTutor(HttpServletResponse response, String fileName) throws IOException, InvalidFormatException {

		exportData(tutorSheetHandler, "excel-model/tutor-model.xlsx", response, fileName);
	}

	// 对 学生 导入 导出模版 导出数据
	public void importStudent(MultipartFile file) throws Exception {

		importData(file, studentSheetHandler);
	}

	public void exportStudentModel(HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {

		Resource resource = new ClassPathResource("excel-model/student-model.xlsx");
		File file = resource.getFile();
		// 输出文件
		ResponseUtil.download(response, file, fileName);
	}

	public void exportStudent(HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {

		exportData(studentSheetHandler, "excel-model/group-model.xlsx", response, fileName);
	}

	// 对 班级（组） 导入 导出模版 导出数据
	public void importGroup(MultipartFile file) throws Exception {

		importData(file, groupSheetHandler);
	}

	public void exportGroupModel(HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {

		Resource resource = new ClassPathResource("excel-model/group-model.xlsx");
		File file = resource.getFile();
		// 输出文件
		ResponseUtil.download(response, file, fileName);
	}

	public void exportGroup(HttpServletResponse response, String fileName) throws Exception {

		exportData(groupSheetHandler, "excel-model/group-model.xlsx", response, fileName);
	}

	public void exportData(HandleExcelExport handler, String modelPath, HttpServletResponse response, String fileName)
			throws IOException, InvalidFormatException {
		Resource resource = new ClassPathResource(modelPath);
		File file = resource.getFile();
		Workbook workbook = new XSSFWorkbook(file);
		handler.handleExportData(workbook);

		// 输出成 excel
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		ResponseUtil.download(response, byteArrayOutputStream, fileName);
		workbook.close();
	}

	// 大数据量的导入
	public void importData(MultipartFile file, SheetContentsHandler sheetContentsHandler) throws Exception {
		// 1.根据excel报表获取OPCPackage
		OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
		// 2.创建XSSFReader
		XSSFReader reader = new XSSFReader(opcPackage);
		// 3.获取SharedStringTable对象
		SharedStringsTable table = reader.getSharedStringsTable();
		// 4.获取styleTable对象
		StylesTable stylesTable = reader.getStylesTable();
		// 5.创建Sax的xmlReader对象
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		// 6.注册事件处理器
		XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable, table, sheetContentsHandler, false);
		xmlReader.setContentHandler(xmlHandler);
		// 7.逐行读取
		XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
		while (sheetIterator.hasNext()) {
			InputStream stream = sheetIterator.next(); // 每一个sheet的流数据
			InputSource is = new InputSource(stream);
			xmlReader.parse(is);
		}
	}

	public static Object getCellValue(Cell cell) {
		// 1.获取到单元格的属性类型
		// 2.根据单元格数据类型获取数据
		Object value = null;
		switch (cell.getCellType()) {
		case STRING:
			value = cell.getStringCellValue();
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// 日期格式
				value = cell.getDateCellValue();
			} else {
				// 数字
				value = cell.getNumericCellValue();
			}
			break;
		case FORMULA:
			// 公式
			value = cell.getCellFormula();
			break;
		default:
			break;
		}
		return value;
	}

}
