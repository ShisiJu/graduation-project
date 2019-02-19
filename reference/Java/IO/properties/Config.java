/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.forestar.core;

import com.forestar.core.util.StringUtils;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import org.springframework.util.Assert;

public class Config {
	private static String url = null;
	private static String supername = null;
	private static String superpassword = null;
	private static boolean isLocalHost = false;
	private static String systemCode = null;
	private static int moduleID = -1;
	private static String geometryServiceUrl = null;
	private static String maxExtent = null;
	private static String wkid = null;
	static Properties dbProps = new Properties();
	private static boolean isOpen;

	private static synchronized void loads() {
		InputStream is = Config.class.getResourceAsStream("/config.properties");
		try {
			dbProps.load(is);
			url = dbProps.getProperty("url");
			Assert.notNull(url, "缺少 url属性,请指定运维服务地址！");
			supername = dbProps.getProperty("super.username");
			Assert.notNull(supername, "缺少 super.username属性,请指定超级管理员登录用户名！");
			superpassword = dbProps.getProperty("super.password");
			Assert.notNull(superpassword, "缺少 super.password属性,请指定超级管理员登录密码！");
			systemCode = dbProps.getProperty("systemCode");
			Assert.notNull(systemCode, "缺少 systemCode属性,请指定系统编号,用于系统标识！");
			String moduleIDStr = dbProps.getProperty("moduleID");
			Assert.notNull(moduleIDStr, "缺少 moduleID属性,请指定项目编号！");
			if (null != moduleIDStr) {
				moduleID = Integer.valueOf(moduleIDStr).intValue();
			}
			isLocalHost = Boolean.parseBoolean(dbProps.getProperty("isLocalHost"));
			geometryServiceUrl = dbProps.getProperty("geometryServiceUrl");
			maxExtent = dbProps.getProperty("maxExtent");
			wkid = dbProps.getProperty("wkid");
		} catch (Exception e) {
			System.err.println("读取文件config.properties失败，请检查文件及参数是否正确！");
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return dbProps.getProperty(key);
	}

	public static int getModuleID() {
		return moduleID;
	}

	public static String getUrl() {
		return url;
	}

	public static String getSupername() {
		return supername;
	}

	public static String getSuperpassword() {
		return superpassword;
	}

	public static boolean isLocalHost() {
		return isLocalHost;
	}

	public static String getSystemCode() {
		return systemCode;
	}

	public static String getGeometryServiceUrl() {
		return geometryServiceUrl;
	}

	public static String getMaxExtent() {
		return maxExtent;
	}

	public static String getWkid() {
		return wkid;
	}

	public static Boolean isOpenLog() {
		String isOpen_ = dbProps.getProperty("logger.openLog");
		if (!(StringUtils.isEmpty(isOpen_))) {
			isOpen = Boolean.valueOf(dbProps.getProperty("logger.openLog")).booleanValue();
		}
		return Boolean.valueOf(isOpen);
	}

	public static String getCasServer() {
		return dbProps.getProperty("cas.server");
	}

	public static String getCasClient() {
		return dbProps.getProperty("cas.client");
	}

	static {
		loads();

		isOpen = false;
	}
}