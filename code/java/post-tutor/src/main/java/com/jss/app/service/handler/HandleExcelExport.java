package com.jss.app.service.handler;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;

public interface HandleExcelExport {

	void handleExportData(Workbook workbook);

	void handleExportData(JSONObject jsonObject, Workbook workbook);
}
