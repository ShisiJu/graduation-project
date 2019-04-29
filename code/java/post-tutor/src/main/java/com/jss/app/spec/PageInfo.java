package com.jss.app.spec;

public enum PageInfo {

	PAGE_INDEX(0), PAGE_SIZE(10);

	@SuppressWarnings("unused")
	private Integer value;

	private PageInfo(Integer value) {
		this.value = value;
	}

}
