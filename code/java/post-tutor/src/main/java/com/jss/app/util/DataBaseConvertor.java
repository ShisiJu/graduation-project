package com.jss.app.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConvertor {

	public static List<Long> toListLong(List<BigInteger> listBigInteger) {
		List<Long> listLong = new ArrayList<>();

		listBigInteger.forEach(bi -> {
			listLong.add(bi.longValue());
		});

		return listLong;
	}

	public static String arrayJoin(List<String> listStr, String ch) {

		StringBuffer sb = new StringBuffer();
		listStr.forEach(str -> {
			sb.append(str);
			sb.append(ch);
		});

		return sb.substring(0, sb.length() - 1);
	}

}
