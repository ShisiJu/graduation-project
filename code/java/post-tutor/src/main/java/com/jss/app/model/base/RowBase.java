package com.jss.app.model.base;

import java.util.Date;

import lombok.Data;

@Data
public class RowBase {
	private long id;
	private Date createTime;
	private Date updateTime;
	private int deleted;
}
