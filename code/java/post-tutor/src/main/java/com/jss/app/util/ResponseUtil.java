package com.jss.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ResponseUtil {

	private static void setHeader(HttpServletResponse response, String fileName) {
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/octect-stream");

	}

	public static void download(HttpServletResponse response, ByteArrayOutputStream byteArrayOutputStream,
			String fileName) throws IOException {

		setHeader(response, fileName);
		OutputStream output = response.getOutputStream();
		response.setContentLength(byteArrayOutputStream.size());
		byteArrayOutputStream.writeTo(output);
		byteArrayOutputStream.close();
		output.close();
	}

//	public static void download(HttpServletResponse response, File file, String fileName) throws IOException {
//
//		setHeader(response, fileName);
//		OutputStream output = response.getOutputStream();
//		byte[] data = FileUtils.readFileToByteArray(file);
//		response.setContentLength(data.length);
//		output.write(data);
//		output.close();
//	}
	
	

	public static void download(HttpServletResponse response, InputStream inputStream, String fileName) throws IOException {
		setHeader(response, fileName);
		OutputStream output = response.getOutputStream();
		byte[] data = IOUtils.toByteArray(inputStream);
		response.setContentLength(data.length);
		output.write(data);
		output.close();
	}

}
