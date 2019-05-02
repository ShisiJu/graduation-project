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
	
	

}
